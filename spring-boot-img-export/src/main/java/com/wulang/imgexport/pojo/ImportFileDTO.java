package com.wulang.imgexport.pojo;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author wulang
 * @create 2021/3/7/21:51
 */
public class ImportFileDTO {

    /**
     * 文件对象
     */
    private MultipartFile dataMultipartFile;

    public MultipartFile getDataMultipartFile() {
        return dataMultipartFile;
    }

    public void setDataMultipartFile(MultipartFile dataMultipartFile) {
        this.dataMultipartFile = dataMultipartFile;
    }

    @Override
    public String toString() {
        return "ImportFileDTO{" +
            "dataMultipartFile=" + dataMultipartFile +
            '}';
    }
}
