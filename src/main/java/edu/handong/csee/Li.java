package edu.handong.csee;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
//import org.apache.commons.io.filefilter.HiddenFileFilter;

public class Li {
	static String path;
	boolean info;
	boolean human;
	boolean all;
	boolean time;
	boolean reverse;
	
	
	public static void main(String[] args) {
		final String dir = System.getProperty("user.dir");
	    path = dir;
	    System.out.println("current dir = " + dir);
	    
		Li myRunner = new Li();
		myRunner.run(args);

	}
	
	private void run(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			File file = new File(path);

			String files[] = file.list();
			
			
			if (info){
				printHelp(options);
				return;
			}
			
			
			
			if(all) {
				

				for(int i=0 ; i<files.length ; i++)
				{
				System.out.println(files[i]);
				}
			}
			
			if(human) {
				File f = null;

				for(int i=0 ; i<files.length ; i++){
					f = new File(files[i]);
					long L = f.length();
					
					System.out.println(files[i] + ": " + readableFileSize(L));
				}
			}
			
			if(time) {
				File f = null;
				long millisec;
				Date dt = null;
				HashMap<Integer, Long> map = new HashMap<Integer, Long>();
				
				for(int i=0 ; i<files.length ; i++){
					f = new File(files[i]);
					millisec = f.lastModified();
					map.put(i, millisec);
				}
				
				Iterator it = sortByValue(map).iterator();
				while(it.hasNext()) {

			            Integer temp = (Integer) it.next();

			            System.out.println(temp + ": " + map.get(temp));


			    }
			}
			
			if(reverse) {
				

				for(int i=files.length-1 ; i >= 0 ; i--)
				{
				System.out.println(files[i]);
				}
			}
		}
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			all = cmd.hasOption("a");
			human = cmd.hasOption("h");
			info = cmd.hasOption("i");
			time = cmd.hasOption("t");
			reverse = cmd.hasOption("r");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	private Options createOptions() {
		Options options = new Options();


		options.addOption(Option.builder("a").longOpt("all")
				.desc("Display all files in the directory")
				.hasArg()     // this option is intended not to have an option value but just an option
				.argName("all files")
				//.required() // this is an optional option. So disabled required().
				.build());
		
		options.addOption(Option.builder("i").longOpt("info")
		        .desc("Help")
		        .build());
		
		options.addOption(Option.builder("h").longOpt("human")
				.desc("Print out the size of the files")
				.hasArg()
				.argName("size of a file")
				.required()
				.build());
		
		options.addOption(Option.builder("t").longOpt("time")
				.desc("Print out the time modified of the files")
				.hasArg()
				.argName("time file modified")
				.required()
				.build());
		
		options.addOption(Option.builder("r").longOpt("reverse")
				.desc("Print out reversed list of files")
				.hasArg()
				.argName("reversed list")
				.required()
				.build());
	

		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "customized ls command";
		String footer ="\nPlease report issues at https://github.com/hongju2024";
		formatter.printHelp("My ls", header, options, footer, true);
	}
	
	public String readableFileSize(long size) {
	    if(size <= 0) return "0";
	    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
	public static List sortByValue(final Map map) {

        List<String> list = new ArrayList();

        list.addAll(map.keySet());

         

        Collections.sort(list,new Comparator() {

             

            public int compare(Object o1,Object o2) {

                Object v1 = map.get(o1);

                Object v2 = map.get(o2);

                 

                return ((Comparable) v2).compareTo(v1);

            }

             

        });

        Collections.reverse(list);

        return list;


    }

}