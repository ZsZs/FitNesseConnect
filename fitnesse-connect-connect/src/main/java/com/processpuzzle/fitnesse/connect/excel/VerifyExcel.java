package com.processpuzzle.fitnesse.connect.excel;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;

import com.processpuzzle.fitnesse.connect.resource.ResourceConnector;

public abstract class VerifyExcel {
   protected XSSFSheet excelSheet;
   protected final String resourcePath;
   protected final String sheetName;

   // constructors
   public VerifyExcel( final String resourcePath, final String sheetName ){
      this.resourcePath = resourcePath;
      this.sheetName = sheetName;
   }

   protected void openWorksheet() throws FileNotFoundException, IOException {
      Resource excelResource = new ResourceConnector( this.resourcePath ).retrieveResource();
      @SuppressWarnings( "resource" ) XSSFWorkbook excelWorkbook = new XSSFWorkbook( excelResource.getInputStream() );
      excelSheet = excelWorkbook.getSheet( sheetName );
   }
}
