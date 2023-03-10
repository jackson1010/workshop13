package TFIP.workshop14.redo;

import java.io.File;
import java.util.List;

import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Workshop14RedoApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Workshop14RedoApplication.class);
		DefaultApplicationArguments appArgs= new DefaultApplicationArguments(args);
		List<String> inputPathList = appArgs.getOptionValues("dataDir");
		
		String dataDir = inputPathList.get(0);

		if(dataDir !=null){
			createDir(dataDir);
		}else{
			System.out.println("exit");
			System.exit(1);
		}
		app.run(args);


}

	private static void createDir(String inputPath) {
		File dir = new File(inputPath);
		boolean isDirExist = dir.mkdir();
	}
}

