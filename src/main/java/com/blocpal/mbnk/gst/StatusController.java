package com.blocpal.mbnk.gst;

import com.blocpal.common.response.ServiceResult;
import com.blocpal.mbnk.gst.service.StatusService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Controller("/status")
@Secured(SecurityRule.IS_ANONYMOUS)
public class StatusController {

    @Inject
    private StatusService statusService;

    @Get("/update")
    public ServiceResult<Map> updatePendingTxnStatus() {
        Map<String,Object> map=new HashMap<>();
        map.put("Total_updated",statusService.doStatusUpdate());
        return new ServiceResult<>(map);
    }
}
