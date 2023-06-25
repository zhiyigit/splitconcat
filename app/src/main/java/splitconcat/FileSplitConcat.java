package splitconcat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileSplitConcat {
  private static final int BUFFER_SIZE = 1065024;

  public static void splitFile(String inputFile, int chunkSize) throws IOException {
    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile))) {
      byte[] buffer = new byte[chunkSize];
      int bytesRead;
      int chunkIndex = 0;

      while ((bytesRead = bis.read(buffer)) > 0) {
        String chunkFileName = String.format("%s%04d", inputFile + "_chunk", chunkIndex);
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(chunkFileName))) {
          bos.write(buffer, 0, bytesRead);
        }
        chunkIndex++;
      }
    }
  }

  public static void concatenateFiles(String filenamePrefix) throws IOException {
    List<String> filenames = new ArrayList<>();

    for (int i = 0; i <= 9999; i++) {
      String filename = String.format("%s_chunk%04d", filenamePrefix, i);
      Path filePath = Paths.get(filename);
      if (Files.exists(filePath)) {
        filenames.add(filename);
      } else {
        break;
      }
    }

    String filenameOutput = filenamePrefix + "_concated";

    concatenateFiles(filenames, filenameOutput);
  }

  public static void concatenateFiles(List<String> inputFiles, String outputFile) throws IOException {
    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {
      for (String inputFile : inputFiles) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile))) {
          byte[] buffer = new byte[BUFFER_SIZE];
          int bytesRead;

          while ((bytesRead = bis.read(buffer)) > 0) {
            bos.write(buffer, 0, bytesRead);
          }
        }
      }
      bos.flush();
    }
  }
}
