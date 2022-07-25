package com.blocpal.mbnk.gst.adapters.response;

import com.blocpal.mbnk.gst.dao.models.DownloadData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DownloadResponse extends CommonResponse{
    private DownloadData data;
}

