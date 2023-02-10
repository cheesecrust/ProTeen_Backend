package com.ProTeen.backend.shelter.controller;

import com.ProTeen.backend.shelter.dto.ShelterFeedbackDTO;
import com.ProTeen.backend.shelter.entity.Shelter;
import com.ProTeen.backend.shelter.service.ShelterService;
import com.ProTeen.backend.user.dto.ResponseDTO;
import com.ProTeen.backend.shelter.dto.ShelterDTO;
import com.ProTeen.backend.shelter.repository.JPAFeedbackRepository;
import com.ProTeen.backend.shelter.repository.JPAShelterRepository;
import com.ProTeen.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("shelter")
public class ShelterController {
    private final ShelterService shelterService;
    private final JPAShelterRepository shelterRepository;
    private final JPAFeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

//    @GetMapping("/read/Ctpv")
//    public ResponseEntity<?> readShelterByCtpvNm(@RequestParam("text") String CtpvNm){
//        List<Shelter> shelterList = shelterService.readByCtpvNm(CtpvNm);
//        ResponseDTO<Shelter> responseDTO = ResponseDTO.<Shelter>builder().data(shelterList).build();
//        return ResponseEntity.ok().body(responseDTO);
//    }
//
//    @GetMapping("/read/Fclt")
//    public ResponseEntity<?> readShelterFcltNm(@RequestParam("text") String FcltNm){
//        List<Shelter> shelterList = shelterService.readByFcltNm(FcltNm);
//        ResponseDTO<Shelter> responseDTO = ResponseDTO.<Shelter>builder().data(shelterList).build();
//        return ResponseEntity.ok().body(responseDTO);
//    }
//
//    @GetMapping("/read")
//    public ResponseEntity<?> readShelterAll(){
//        List<Shelter> shelterList = shelterService.readAll();
//        List<ShelterFeedbackDTO.Total> dtos = shelterList.stream().map(ShelterFeedbackDTO.Total::new).toList();
//        ResponseDTO<ShelterFeedbackDTO.Total> responseDTO = ResponseDTO.<ShelterFeedbackDTO.Total>builder().data(dtos).build();
//        return ResponseEntity.ok().body(responseDTO);
//    }

    @GetMapping("/readsimple/Ctpv")
    public ResponseEntity<?> readShelterDtoByCtpvNm(@RequestParam("text") String CtpvNm){
        try {
            List<ShelterDTO> shelterList = shelterService.readDtoByCtpvNm(CtpvNm);
            ResponseDTO<ShelterDTO> responseDTO = ResponseDTO.<ShelterDTO>builder().data(shelterList).build();
            return ResponseEntity.ok().body(responseDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/readsimple/Fclt")
    public ResponseEntity<?> readShelterDtoFcltNm(@RequestParam("text") String FcltNm){
        try {
            List<ShelterDTO> shelterList = shelterService.readDtoByFcltNm(FcltNm);
            ResponseDTO<ShelterDTO> responseDTO = ResponseDTO.<ShelterDTO>builder().data(shelterList).build();
            return ResponseEntity.ok().body(responseDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/readsimple")
    public ResponseEntity<?> readShelterDtoAll(){
        try {
            List<ShelterDTO> shelterList = shelterService.readDtoAll();
            ResponseDTO<ShelterDTO> responseDTO = ResponseDTO.<ShelterDTO>builder().data(shelterList).build();
            return ResponseEntity.ok().body(responseDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/read/{shelter_id}")
    public ResponseEntity<?> readDetailShelter(@PathVariable Long shelter_id){
        try {
            List<Shelter> shelterList = shelterService.readSheletrDetail(shelter_id);
            List<ShelterFeedbackDTO.Total> dtos = shelterList.stream().map(ShelterFeedbackDTO.Total::new).toList();
            ResponseDTO<ShelterFeedbackDTO.Total> responseDTO = ResponseDTO.<ShelterFeedbackDTO.Total>builder().data(dtos).build();
            return ResponseEntity.ok().body(responseDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bussiness/update")
    public ResponseEntity<?> updateNewApi () {
        StringBuffer result = new StringBuffer();
        String endpoint = "http://apis.data.go.kr/1383000/gmis/teenRAreaService/getTeenRAreaList";
        String key = "ctunwl8u7zRlMNifImrqNu1tPkMz3sL681qF%2Bfp7RvQY9kgqyZAHpeXRF3ts8E11ZlcZZp5omrJe5lgfHIEeCQ%3D%3D";
        int pageNo = 1;
        int numOfRows = 1000;
        String type = "json";
        try {
            URL url = new URL(endpoint +"?serviceKey="+ key +"&pageNo="+ pageNo +"&numOfRows="+ numOfRows +"&type="+ type);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());

            BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result.append(bf.readLine());

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());
            JSONObject responseResult = (JSONObject)jsonObject.get("response");
            JSONObject bodyResult = (JSONObject)responseResult.get("body");
            JSONObject itemsResult = (JSONObject)bodyResult.get("items");
            JSONArray jsonArray = (JSONArray) itemsResult.get("item");

            System.out.println("jsonArray = " + jsonArray);
                Long i = 1L;
            for (Object o : jsonArray) {
                JSONObject object = (JSONObject) o;
                // 전부 받아주기...
                String rprsTelno = (String) object.get("rprsTelno");
                String fxno = (String) object.get("fxno");
                String emlAddr = (String) object.get("emlAddr");
                String cpctCnt = (String) object.get("cpctCnt");
                String etrTrgtCn = (String) object.get("etrTrgtCn");
                String etrPrdCn = (String) object.get("etrPrdCn");
                String nrbSbwNm = (String) object.get("nrbSbwNm");
                String nrbBusStnNm = (String) object.get("nrbBusStnNm");
                String crtrYmd = (String) object.get("crtrYmd");
                String expsrYn = (String) object.get("expsrYn");
                String rmrkCn = (String) object.get("rmrkCn");
                String fcltNm = (String) object.get("fcltNm");
                String operModeCn = (String) object.get("operModeCn");
                String fcltTypeNm = (String) object.get("fcltTypeNm");
                String ctpvNm = (String) object.get("ctpvNm");
                String sggNm = (String) object.get("sggNm");
                String roadNmAddr = (String) object.get("roadNmAddr");
                String lotnoAddr = (String) object.get("lotnoAddr");
                String lat = (String) object.get("lat");
                String lot = (String) object.get("lot");
                String hmpgAddr = (String) object.get("hmpgAddr");

                Shelter shelter = Shelter.builder()
                        .id(i)
                        .rprsTelno(rprsTelno)
                        .fxno(fxno)
                        .emlAddr(emlAddr)
                        .cpctCnt(cpctCnt)
                        .etrTrgtCn(etrTrgtCn)
                        .etrPrdCn(etrPrdCn)
                        .nrbSbwNm(nrbSbwNm)
                        .nrbBusStnNm(nrbBusStnNm)
                        .crtrYmd(crtrYmd)
                        .expsrYn(expsrYn)
                        .rmrkCn(rmrkCn)
                        .fcltNm(fcltNm)
                        .operModeCn(operModeCn)
                        .fcltTypeNm(fcltTypeNm)
                        .ctpvNm(ctpvNm)
                        .sggNm(sggNm)
                        .roadNmAddr(roadNmAddr)
                        .lotnoAddr(lotnoAddr)
                        .lat(lat)
                        .lot(lot)
                        .hmpgAddr(hmpgAddr)
                        .build();
                System.out.println("shelter.getId() = " + shelter.getId());
                shelterRepository.save(shelter);
                i += 1;
            }
            return ResponseEntity.ok().body("Update Successfully");


        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().body(error);
        }
    }

}
