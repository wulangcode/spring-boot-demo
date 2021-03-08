package com.wulang.imgexport.util;

import com.wulang.imgexport.pojo.ImportFileDTO;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 读取excel 图片工具类
 *
 * @author wulang
 * @create 2021/3/7/21:29
 */
public class ExcelUtil {

    public void importPendSettlementData(ImportFileDTO importOutsourceUser) {
        Workbook wb = readExcel(importOutsourceUser);
        if (null == wb) {
            System.out.println("读取文件失败");
            return;
        }
        try {
            Map<String, Map<Integer, Map<Integer, String>>> sheets = getStringMapMap(wb);
            //
            Set<String> keys = sheets.keySet();
            List<String> steetsNames = new ArrayList<>();

            //获取所有的key ==页名称
            steetsNames.addAll(keys);
            //遍历所有的列
            for (int i = 0; i < sheets.size(); i++) {
                //根据页名称获取页
                Map<Integer, Map<Integer, String>> rows = sheets.get(steetsNames.get(i));
                System.out.println("页名称:" + steetsNames.get(i));
                //根据所有的行 遍历
                for (int j = 0; j < rows.size(); j++) {
                    Map<Integer, String> columns = rows.get(j);
                    //遍历当前行的所有列
                    for (int k = 0; k < columns.size(); k++) {
                        //输出
                        //TODO 此处根据自己业务逻辑做映射对象处理，若想抽成公共方法，可以考虑反射
                        System.out.print("\t" + columns.get(k));

                    }
                    System.out.println();
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    private Workbook readExcel(ImportFileDTO importOutsourceUser) {
        Workbook wb = null;
        try {
            InputStream inputStream = importOutsourceUser.getDataMultipartFile().getInputStream();
            wb = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * 本地导入图片
     *
     * @param excelFilePath 文件绝对地址
     * @return
     * @throws IOException
     */
    public static Map<String, Map<Integer, Map<Integer, String>>> readExcelToMap(String excelFilePath) throws IOException {
        Workbook wb = readExcel(excelFilePath);

        return getStringMapMap(wb);
    }

    /**
     * 此方法可以是本地上传或通过网络上传
     *
     * @param wb 文件对象
     * @return excel对象集合
     * @throws IOException
     */
    private static Map<String, Map<Integer, Map<Integer, String>>> getStringMapMap(Workbook wb) throws IOException {
        // 声明所有页的集合
        Map<String, Map<Integer, Map<Integer, String>>> mapSheet = new LinkedHashMap<>();
        Sheet sheet = null;
        Row row = null;
        if (wb != null) {
            // 获取总页数
            int pageSize = wb.getNumberOfSheets();
            for (int i = 0; i < pageSize; i++) {

                // 声明当前页的行和列
                Map<Integer, Map<Integer, String>> map = new HashMap<>();
                // 获取当前页
                sheet = wb.getSheetAt(i);
                //声明当前页图片的集合
                /*Map<String, PictureData> pMap = null;
                if (excelFilePath.endsWith(".xls")) {
                    pMap = getPictures1((HSSFSheet) sheet);
                } else {
                    pMap = getPictures2((XSSFSheet) sheet);
                }*/
                //获取当前页的所有图片
                Map<String, PictureData> pMap = getPictures2((XSSFSheet) sheet);
                //获取页名称
                String sheetName = sheet.getSheetName();

                // System.out.println("获取当前页的最大行数");
                int rowSize = sheet.getPhysicalNumberOfRows();

                //System.out.println("总行数:"+rowSize);
                // System.out.println("遍历所有行");
                for (int j = 0; j < rowSize; j++) {
                    // System.out.println("获取第"+j+"行");
                    row = sheet.getRow(j);
                    // System.out.println("获取当前页的最大列数");
                    int columnSize = row.getPhysicalNumberOfCells();
                    // 声明当前列
                    Map<Integer, String> columnMap = new HashMap<>(500);
                    //System.out.println("列大小:"+columnSize);
                    for (int j2 = 0; j2 < columnSize; j2++) {
                        // System.out.println("获取第"+j2+"列的内容");
                        String value = (String) getCellFormatValue(row.getCell(j2));
                        // 添加当前列的内容 j2代表第几列 value是内容
                        columnMap.put(j2, value);

                    }
                    // 添加当前行的内容 j代表第几行 value是列的内容 意思是第几行第几列的内容
                    map.put(j, columnMap);
                }
                //解析图片并上传到服务器 进行文件上传后 返回上传地址 并根据图片所在的表格位置 赋值表格位置为 上传后的返回地址
                Object[] key = pMap.keySet().toArray();

                for (int v = 0; v < pMap.size(); v++) {
                    //获取图像数据对象
                    PictureData pic = pMap.get(key[v]);
                    //获取文件名称
                    String picName = key[v].toString();
                    //图片数据
                    byte[] data = pic.getData();
                    String uploadFile = OSSUtils.byteUploadFile(data);
                    String[] split = picName.split("-");
                    Integer rowIndex = Integer.parseInt(split[0]);
                    Integer columnIndex = Integer.parseInt(split[1]);
                    Map<Integer, String> columns = map.get(rowIndex);
                    if (columns == null) {
                        continue;
                    }
                    columns.put(columnIndex, uploadFile);
                }
                // 添加当前页的所有内容
                mapSheet.put(sheetName, map);
            }
        }
        return mapSheet;
    }

    // 读取excel
    private static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Map<String, PictureData> getPictures1(HSSFSheet sheet) throws IOException {
        Map<String, PictureData> map = new HashMap<>();
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        for (HSSFShape shape : list) {

            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture) shape;

                HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                PictureData pdata = picture.getPictureData();
                // 行号-列号
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1();
                map.put(key, pdata);
            }
        }
        return map;
    }

    public static Map<String, PictureData> getPictures2(XSSFSheet sheet) {
        Map<String, PictureData> map = new HashMap<>();
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = null;
                    try {
                        picture = (XSSFPicture) shape;
                    } catch (ClassCastException e) {
                        continue;
                    }
                    try {
                        picture.getPreferredSize();
                    } catch (NullPointerException e) {
                        continue;
                    }
                    if (picture.getPreferredSize() == null) {
                        continue;
                    }
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker marker = anchor.getFrom();
                    String key = marker.getRow() + "-" + marker.getCol();
                    map.put(key, picture.getPictureData());
                }
            }
        }
        return map;
    }

    private static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            // 判断cell类型
            switch (cell.getCellTypeEnum()) {
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                case NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case FORMULA: {
                    try {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cellValue = cell.getDateCellValue();
                        } else {
                            cellValue = String.valueOf(cell.getNumericCellValue());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        cellValue = "";
                        break;
                    }
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }
}
