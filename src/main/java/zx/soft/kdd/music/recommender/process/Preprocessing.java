package zx.soft.kdd.music.recommender.process;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author wanggang
 *
 */
public class Preprocessing {

	private final int numberOfChunks;
	private final String outFilePrefix;
	private final int numberOfSongsPerChunk;
	private final String inFile;
	private final Scanner scan;
	private FileInputStream file;
	private String currentUserLine;
	private int currentUserRatingCount;
	private int currentUserRatingSum;
	private final Chunk[] chunks;

	public Preprocessing(String inFile, String outFilePrefix, int numberOfChunks, int numberOfSongs) {
		this.inFile = inFile;
		this.outFilePrefix = outFilePrefix;
		this.numberOfChunks = numberOfChunks;
		this.numberOfSongsPerChunk = (int) Math.ceil(numberOfSongs / ((double) numberOfChunks));
		chunks = new Chunk[numberOfChunks];

		for (int i = 0; i < numberOfChunks; i++) {
			chunks[i] = new Chunk(outFilePrefix + i + ".txt");
		}

		try {
			file = new FileInputStream(inFile);
		} catch (FileNotFoundException e) {
			System.err.println("Database file " + inFile + " not found.");
			System.exit(1);
		}
		scan = new Scanner(file);

	}

	public void close() {
		scan.close();
		try {
			file.close();
		} catch (IOException ex) {
			System.err.println("Unable to close database file " + inFile + "\n" + ex);
			System.exit(1);
		}

		for (Chunk chunk : chunks)
			chunk.close();
	}

	public void parse() {
		while (scan.hasNext()) {
			String line = scan.nextLine();
			if (line.contains("|")) {
				printChunks(currentUserLine + "|" + currentUserAverageRating());
				currentUserLine = line;
				currentUserRatingCount = 0;
				currentUserRatingSum = 0;
			} else {
				int[] songAndRating = strArrayToIntArray(line.split("\t"));
				if (songAndRating.length != 2) {
					System.err.println("Song line did not have two elements on it: " + line);
					System.exit(1);
				}
				try {
					chunks[songAndRating[0] / numberOfSongsPerChunk].addSongRating(line);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println("Tried to write to " + songAndRating[0] / numberOfSongsPerChunk + "\n" + e);
					System.exit(1);
				}

				currentUserRatingSum += songAndRating[1];
				currentUserRatingCount++;
			}

		}
		printChunks(currentUserLine + "|" + currentUserAverageRating());

	}

	private static int[] strArrayToIntArray(String[] line) {
		int[] infoLine = new int[2];
		for (int i = 0; i < 2; i++) {
			int info = ("none".equals(line[i].toLowerCase())) ? -1 : Integer.valueOf(line[i]).intValue();
			infoLine[i] = info;
		}
		return infoLine;
	}

	private double currentUserAverageRating() {
		return ((double) currentUserRatingSum) / currentUserRatingCount;

	}

	private void printChunks(String userLine) {
		for (Chunk chunk : chunks) {
			chunk.printSongs(userLine);
		}
	}

	public int getNumberOfChunks() {
		return numberOfChunks;
	}

	public int getNumberOfSongsPerChunk() {
		return numberOfSongsPerChunk;
	}

	public String getOutFilePrefix() {
		return outFilePrefix;
	}

}
