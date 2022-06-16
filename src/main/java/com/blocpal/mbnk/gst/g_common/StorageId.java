package com.blocpal.mbnk.gst.g_common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class StorageId {

    @Getter
    @Setter
    String bucket;

    @Getter @Setter
    String name;
}