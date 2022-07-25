package com.blocpal.mbnk.gst.dao.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Base64;

@Data
@EqualsAndHashCode(callSuper=false)
public class DownloadData {
        private String pdfBytes;
        private String cpin;
        private String apiName;
        private String gibTxnId;
        private String pdfName;
        public String getDecodedPdfBytes(){
            // Getting decoder
            Base64.Decoder decoder = Base64.getDecoder();
            // Decoding string
            byte[] decodedData=decoder.decode(this.pdfBytes);
            if(decodedData.length>0)
                return new String(decodedData);
            else
                return "error";
        }
}
