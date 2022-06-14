package com.transit.ticketing.controller;

import com.transit.ticketing.dto.BankingSheetDataDto;
import com.transit.ticketing.dto.BankingSheetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class BankingSheetController {
    private static final Logger LOG = LoggerFactory.getLogger(BankingSheetController.class);

    @GetMapping(value="/api/v1/secure/bankingSheet")
    public ResponseEntity<Object> getBankingSheet() {
        LOG.info("Generating banking sheet");
        Map<String, Object> bankingSheets = new HashMap<>();

        List<BankingSheetDto> bankingSheetDtos = new ArrayList<>();

        BankingSheetDto bankingSheetDto1 = new BankingSheetDto();
        bankingSheetDto1.setStationCode("1");
        bankingSheetDto1.setStationName("Ernakulam");
        bankingSheetDto1.setBankingSheetDate("04/11/2021 3:54:04 PM");
        bankingSheetDto1.setTotalRackAmount("28495");
        bankingSheetDto1.setTotalCrAmount("0");
        bankingSheetDto1.setTotalCardAmount("0");
        bankingSheetDto1.setNetAmount("28945");
        List<BankingSheetDataDto> dataSheets = new ArrayList<>();
        Map<String,Integer> col = new HashMap<>();
        col.put("rupees",1083);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("S 28", "7:31", "12111031", "Ernakulam",
                "EKM - Vypin", "Salilan T. M", col, "290", "0", "0", "290","793"));
        col = new HashMap<>();
        col.put("rupees",846);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("F C-02", "14:23", "121110310", "Ernakulam",
                "Mattancherry", "Anil Kumar K A", col, "392", "0", "0", "392","454"));
        col = new HashMap<>();
        col.put("rupees",8622);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("C5", "13:19", "12111032", "Ernakulam",
                "Fort Kochi", "Ibrahim M S", col, "128", "0", "0", "128","8494"));
        col = new HashMap<>();
        col.put("rupees",3252);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("C3", "13:08", "12111034", "Ernakulam",
                "Embarkation", "K K Prasad", col, "118", "0", "0", "118","3134"));
        col = new HashMap<>();
        col.put("rupees",7820);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("C2", "13:42", "12111035", "Ernakulam",
                "EKM", "Rajan K R", col, "105", "0", "0", "105","7715"));
        col = new HashMap<>();
        col.put("rupees",5892);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("C1", "13:28", "12111036", "Ernakulam",
                "EKM", "Binu C V", col, "840", "0", "0", "840","5052"));
        col = new HashMap<>();
        col.put("rupees",1122);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("S 28", "12:50", "12111037", "Ernakulam",
                "12.45 - Terminal", "Rajesh V", col, "400", "0", "0", "400","722"));
        col = new HashMap<>();
        col.put("rupees",1841);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("S 34", "13:20", "12111038", "Ernakulam",
                "Mulavukad", "Sameesh M S", col, "430", "0", "0", "430","1411"));
        col = new HashMap<>();
        col.put("rupees",1090);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("F C-01", "13:48", "12111039", "Ernakulam",
                "Mattancherry", "Baiju K V", col, "370", "0", "0", "370","720"));

        bankingSheetDto1.setSheets(dataSheets);

        BankingSheetDto bankingSheetDto2 = new BankingSheetDto();
        bankingSheetDto2.setStationCode("2");
        bankingSheetDto2.setStationName("Kochi");
        bankingSheetDto2.setBankingSheetDate("05/11/2022 5:34:04 PM");
        bankingSheetDto2.setTotalRackAmount("28495");
        bankingSheetDto2.setTotalCrAmount("0");
        bankingSheetDto2.setTotalCardAmount("0");
        bankingSheetDto2.setNetAmount("28945");

        dataSheets = new ArrayList<>();
        col = new HashMap<>();
        col.put("rupees",1083);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("S 28", "7:31", "12111031", "Fort Kochi",
                "EKM - Vypin", "Salilan T. M", col, "290", "0", "0", "290","793"));
        col = new HashMap<>();
        col.put("rupees",846);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("F C-02", "14:23", "121110310", "Fort Kochi",
                "Mattancherry", "Anil Kumar K A", col, "392", "0", "0", "392","454"));
        col = new HashMap<>();
        col.put("rupees",8622);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("C5", "13:19", "12111032", "Fort Kochi",
                "Ernakulam", "Ibrahim M S", col, "128", "0", "0", "128","8494"));
        col = new HashMap<>();
        col.put("rupees",3252);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("C3", "13:08", "12111034", "Fort Kochi",
                "Embarkation", "K K Prasad", col, "118", "0", "0", "118","3134"));
        col = new HashMap<>();
        col.put("rupees",7820);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("C2", "13:42", "12111035", "Fort Kochi",
                "EKM", "Rajan K R", col, "105", "0", "0", "105","7715"));
        col = new HashMap<>();
        col.put("rupees",5892);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("C1", "13:28", "12111036", "Fort Kochi",
                "EKM", "Binu C V", col, "840", "0", "0", "840","5052"));
        col = new HashMap<>();
        col.put("rupees",1122);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("S 28", "12:50", "12111037", "Fort Kochi",
                "12.45 - Terminal", "Rajesh V", col, "400", "0", "0", "400","722"));
        col = new HashMap<>();
        col.put("rupees",1841);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("S 34", "13:20", "12111038", "Fort Kochi",
                "Mulavukad", "Sameesh M S", col, "430", "0", "0", "430","1411"));
        col = new HashMap<>();
        col.put("rupees",1090);col.put("paise", 0);
        dataSheets.add(new BankingSheetDataDto("F C-01", "13:48", "12111039", "Fort Kochi",
                "Mattancherry", "Baiju K V", col, "370", "0", "0", "370","720"));
        bankingSheetDto2.setSheets(dataSheets);

        bankingSheetDtos.add(bankingSheetDto1);
        bankingSheetDtos.add(bankingSheetDto2);
        bankingSheets.put("bankingSheets", bankingSheetDtos);

        return ResponseEntity.ok().body(bankingSheets);
    }

}
