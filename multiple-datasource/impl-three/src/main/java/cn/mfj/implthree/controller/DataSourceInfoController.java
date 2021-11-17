package cn.mfj.implthree.controller;

import cn.mfj.implthree.common.utils.DataSourceUtil;
import cn.mfj.implthree.entity.DataSourceInfo;
import cn.mfj.implthree.service.DataSourceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author favian.meng on 2021-10-13
 */
@RestController
@RequestMapping("/dataSourceInfos")
public class DataSourceInfoController {

    private DataSourceInfoService service;

    @Autowired
    public void setService(DataSourceInfoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DataSourceInfo> create(@Valid @RequestBody DataSourceInfo dataSourceInfo) {
        service.save(dataSourceInfo);
        DataSourceUtil.addDataSource(dataSourceInfo);
        return ResponseEntity.ok(dataSourceInfo);
    }
}
