import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author tyler spring 
 * 4/10/2023 
 * Project 1 
 * CMSC 451 7380 
 * The reportGen class. Here is the second part of the assignment. After the benchmarks are
 *ran and the files are created, the user is prompted to select a file
 *via filechooser. Depending which of the two they select, the files
 *data is read in through the writeToFile method using stringbuilder
 *and the data is formatted to a window in the required columns and
 * order based on the data created and sorted beforehand.
 */
public class reportGen {

	public static void runReport() {
		JFileChooser ch = new JFileChooser();

		int ans = ch.showOpenDialog(null);

		if (ans == JFileChooser.APPROVE_OPTION) {
			File f = ch.getSelectedFile();
			try {
// All the lines of data being read and window layout and headers created.
				List<String> lines = Files.readAllLines(f.toPath());

				JFrame fr = new JFrame("Sorting Report");
				JTable tb = new JTable(12, 5);

				tb.getColumnModel().getColumn(0).setHeaderValue("Size");
				tb.getColumnModel().getColumn(1).setHeaderValue("Avg Count");
				tb.getColumnModel().getColumn(2).setHeaderValue("Coef Count");
				tb.getColumnModel().getColumn(3).setHeaderValue("Avg Time");
				tb.getColumnModel().getColumn(4).setHeaderValue("Coef Time");

				for (int i = 0; i < 12; i++) {
// Data is read and parsed as integers and after an annoying amount of
// debugging, uses the correct split.
					int data = Integer.parseInt(lines.get(i).split("\\s+")[0]);
					// different lists for the integer values read in and the times read in.
					List<Integer> crits = new ArrayList<>();
					List<Long> time = new ArrayList<>();
					for (int j = 1; j < 41; j += 2) {
						crits.add(Integer.parseInt(lines.get(i).split("\\s+")[j]));
						time.add(Long.parseLong(lines.get(i).split("\\s+")[j + 1]));
					}
// calculations for the required data in the window here.
// There probably is a better way to do this, but given time restraints and me
// just wanting
// to be done with this. I used stream and mapToInt because once it worked I
// left it alone.
					int avgCrits = (int) Math.round(crits.stream().mapToInt(Integer::intValue).average().orElse(0));
					long avgTime = Math.round(time.stream().mapToLong(Long::longValue).average().orElse(0));
					double cvCrits = calcCoef(crits);
					double cvTime = calcCoef(time);
//table layout for the data.
					tb.setValueAt(data, i, 0);
					tb.setValueAt(avgCrits, i, 1);
					tb.setValueAt(String.format("%.2f", cvCrits), i, 2);
					tb.setValueAt(avgTime, i, 3);
					tb.setValueAt(String.format("%.2f", cvTime), i, 4);
				}

				fr.getContentPane().add(new JScrollPane(tb));
				fr.pack();
				fr.setVisible(true);

				reportGen.writeToFile(f, lines, tb);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

//writeToFile method. Takes a file type, string arraylist and table object.
	private static void writeToFile(File saveFile, List<String> lines, JTable tb) {
		try {
// created to write the data to the table.
			BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));

			for (int i = 0; i < lines.size(); i++) {
				writer.write(lines.get(i));
				writer.newLine();
			}
//nested for loops to correctly fill rows and columns with the data using stringbuilder.
			for (int i = 0; i < tb.getRowCount(); i++) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < tb.getColumnCount(); j++) {
					Object value = tb.getValueAt(i, j);
					sb.append(value.toString());
					sb.append(" ");
				}
				writer.write(sb.toString());
				writer.newLine();
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//calculations for the table. Again, stream and mapTo worked. So they're staying.
// It does use wildcard for a parameter and extends Number class. Only used this
// a handful of times so glad it worked.
	private static double calcCoef(List<? extends Number> crits) {
		double mean = crits.stream().mapToDouble(Number::doubleValue).average().orElse(0);
		double sumSq = crits.stream().mapToDouble(x -> Math.pow(x.doubleValue() - mean, 2)).sum();
		double coef = sumSq / (crits.size() - 1);
		double standard = Math.sqrt(coef);
		return standard / mean * 100;
	}
}

