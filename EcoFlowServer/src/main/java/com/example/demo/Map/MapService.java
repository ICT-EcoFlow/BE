package com.example.demo.Map;

//import net.nurigo.java_sdk.api.Message;
//import net.nurigo.java_sdk.exceptions.CoolsmsException;
import java.net.URL;
//문자
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
//xml
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;





@Service //스프링이 관리해주는 객체 == 스프링 빈
@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할 
public class MapService {
	

    private final StatRepository statRepository;
    private final LocationRepository locationRepository;

    
 
    
    public Map<String, Object> getInfoByStatNm(String statNm) {
        // statNm을 사용하여 location 테이블에서 statId를 가져온다
        List<LocationEntity> locations = locationRepository.findByStatNmContaining(statNm);
        
        if (locations.isEmpty()) {
            throw new RuntimeException("해당 statNm에 대한 정보가 없습니다.");
        }

        // statId를 가져온다
        String statId = locations.get(0).getStatId();

        // statId를 사용하여 stat 테이블에서 정보를 가져온다
        List<StatEntity> stats = statRepository.findByStatId(statId);

        // 결과를 JSON 형태로 구성
        Map<String, Object> result = new HashMap<>();
        result.put("locations", locations);
        result.put("stats", stats);

        return result;
    }
   
    
    // 위치 기반 검색 기능 추가
    public Map<String, Object> getLocationsNear(double latitude, double longitude, double radius) {
        double earthRadius = 6371; // 지구의 반경 (킬로미터)

        // 반경을 라디안으로 변환
        double radiusInDegrees = radius / earthRadius * (180 / Math.PI);

        double minLat = latitude - radiusInDegrees;
        double maxLat = latitude + radiusInDegrees;
        double minLon = longitude - radiusInDegrees / Math.cos(latitude * Math.PI / 180);
        double maxLon = longitude + radiusInDegrees / Math.cos(latitude * Math.PI / 180);

        List<LocationEntity> nearbyLocations = locationRepository.findLocationsWithinBounds(minLat, maxLat, minLon, maxLon);

        Set<String> statIds = new HashSet<>();
        for (LocationEntity location : nearbyLocations) {
            statIds.add(location.getStatId());
        }

        List<StatEntity> stats = statRepository.findByStatIdIn(statIds);

        Map<String, Object> result = new HashMap<>();
        result.put("locations", nearbyLocations);
        result.put("stats", stats);

        return result;
    }
     
     // tag값의 정보를 가져오는 메소드
     private static String getTagValue(String tag, Element eElement) {
         NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes(); // getChildNodes()메소드는 자식
                                                                                          // 노드들을 NodeList타입으로 반환한다.
         Node nValue = (Node) nlList.item(0);
         if (nValue == null)
             return null;
         return nValue.getNodeValue();
     }
     
     
     //지역 업데이트
     public void updateLocationInfo() {
  	        
         System.out.println("충전소 정보 업데이트");
         //mapRepository.deleteAllFromlocation();
        
 	   int page = 1;
          String keyword = "50";

          try {
              while (true) {
                  String url = "https://apis.data.go.kr/B552584/EvCharger/getChargerInfo?serviceKey=iifF6nWLmR%2BPgzn%2BZKQfpfbSa%2FxrnJe8cRoVMGBOYvaLg0iv2dluN%2BamznkrvFKRPIQHCZkfU4shudRWucbZag%3D%3D&zcode=50&numOfRows=100&pageNo=" + page;

                  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                  Document doc = dBuilder.parse(new URL(url).openStream());

                  doc.getDocumentElement().normalize();

                  NodeList nList = doc.getElementsByTagName("item");

                  for (int temp = 0; temp < nList.getLength(); temp++) {
                      Node nNode = nList.item(temp);
                      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                          Element eElement = (Element) nNode;
                          if (getTagValue("zcode", eElement).contains(keyword)) {
                              LocationEntity locationEntity = new LocationEntity();
                              locationEntity.setStatNm(String.valueOf(getTagValue("statNm", eElement)));
                              locationEntity.setStatId(String.valueOf(getTagValue("statId", eElement)));
                              locationEntity.setAddr(String.valueOf(getTagValue("addr", eElement)));
                              locationEntity.setLocation(String.valueOf(getTagValue("location", eElement)));
                              locationEntity.setLat(Double.valueOf(getTagValue("lat", eElement)));
                              locationEntity.setLng(Double.valueOf(getTagValue("lng", eElement)));
                              locationEntity.setUseTime(String.valueOf(getTagValue("useTime", eElement)));
                              locationEntity.setBnm(String.valueOf(getTagValue("bnm", eElement)));
                              locationEntity.setBusiNm(String.valueOf(getTagValue("busiNm", eElement)));
                              locationEntity.setBusiCall(String.valueOf(getTagValue("busiCall", eElement)));
                              locationEntity.setZcode(String.valueOf(getTagValue("zcode", eElement)));
                              locationEntity.setZscode(String.valueOf(getTagValue("zscode", eElement)));
                              locationEntity.setKind(String.valueOf(getTagValue("kind", eElement)));
                              locationEntity.setKindDetail(String.valueOf(getTagValue("kindDetail", eElement)));
                              locationEntity.setParkingFree(String.valueOf(getTagValue("parkingFree", eElement)));
                              locationEntity.setNote(String.valueOf(getTagValue("note", eElement)));
                              locationEntity.setStat(String.valueOf(getTagValue("stat", eElement)));
                              locationRepository.save(locationEntity);
                          }
                      }
                  }

                  page += 1;
                  System.out.println("page number : " + page);

                  if (nList.getLength() == 0) {
                      break;
                  }
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
          
      }
    
     
     //충전소 업데이트
     public void updateStatInfo() {
  
       // mapRepository.deleteAllFromStat();
        
 	   int page = 1;
          String keyword = "50";

          try {
              while (true) {
                  String url = "https://apis.data.go.kr/B552584/EvCharger/getChargerInfo?serviceKey=iifF6nWLmR%2BPgzn%2BZKQfpfbSa%2FxrnJe8cRoVMGBOYvaLg0iv2dluN%2BamznkrvFKRPIQHCZkfU4shudRWucbZag%3D%3D&zcode=50&numOfRows=100&pageNo=" + page;

                  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                  Document doc = dBuilder.parse(new URL(url).openStream());

                  doc.getDocumentElement().normalize();

                  NodeList nList = doc.getElementsByTagName("item");

                  for (int temp = 0; temp < nList.getLength(); temp++) {
                      Node nNode = nList.item(temp);
                      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                          Element eElement = (Element) nNode;
                          if (getTagValue("zcode", eElement).contains(keyword)) {
                              StatEntity statEntity = new StatEntity();
                              statEntity.setStatId(String.valueOf(getTagValue("statId", eElement)));
                              statEntity.setChgerId(String.valueOf(getTagValue("chgerId", eElement)));
                              statEntity.setChgerType(String.valueOf(getTagValue("chgerType", eElement)));
                              statEntity.setStat(String.valueOf(getTagValue("stat", eElement)));
                              statEntity.setLastTsdt(String.valueOf(getTagValue("lastTsdt", eElement)));
                              statEntity.setNowTsdt(String.valueOf(getTagValue("nowTsdt", eElement)));
                              statEntity.setOutput(String.valueOf(getTagValue("output", eElement)));
                              statEntity.setMethod(String.valueOf(getTagValue("method", eElement)));
                              statEntity.setLimitYn(String.valueOf(getTagValue("limitYn", eElement)));
                              statEntity.setLimitDetail(String.valueOf(getTagValue("limitDetail", eElement)));
                              statRepository.save(statEntity);
                          }
                      }
                  }

                  page += 1;
                  System.out.println("page number : " + page);

                  if (nList.getLength() == 0) {
                      break;
                  }
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
          
      }
   
    
}