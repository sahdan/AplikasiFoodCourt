/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifoodcourt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTable;

/**
 * @author ashraf
 * 
 */
public class CsvFileWriter {
	
	//Delimiter used in CSV file

	public static boolean writeCsvFile(JTable tblPrint, String fileName, String fileDir) {
            boolean berhasil = true;
            //Create new students objects
            String header = "";
            int col = tblPrint.getColumnCount();
            int row = tblPrint.getRowCount();
            for (int i = 0; i < col; i++) {
                if (i != col-1)
                    header = header + tblPrint.getColumnName(i) + ",";
                else
                    header = header + tblPrint.getColumnName(i);
            }
            
            System.out.println(header);
            System.out.println(fileDir);
            System.out.println(fileName);
            //Create a new list of student objects
            
            if (!fileName.endsWith(".csv")) {
                fileName = fileName + ".csv";
            }

            FileWriter fileWriter = null;

            try {
                fileWriter = new FileWriter(new File(fileDir, fileName));

                //Write the CSV file header
                fileWriter.append(header);

                //Add a new line separator after the header
                fileWriter.append("\n");

                //Write to the CSV file
                for (int j = 0; j < row; j++) {
                    for (int i = 0; i < col; i++) {
                        if (i != col-1)
                            fileWriter.append(tblPrint.getValueAt(j,i).toString() + ",");
                        else
                            fileWriter.append(tblPrint.getValueAt(j,i).toString() + "\n");
                    }
                }
                berhasil = true;
                

            } catch (IOException e) {
                    System.out.println("Error in CsvFileWriter !!!");
            } finally {

                try {
                        fileWriter.flush();
                        fileWriter.close();
                } catch (IOException e) {
                        System.out.println("Error while flushing/closing fileWriter !!!");
                }

            }
            return berhasil;
	}
}
