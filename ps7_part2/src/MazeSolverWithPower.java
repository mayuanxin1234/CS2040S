import java.util.ArrayList;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 1, 0 }, // South
			{ 0, 1 }, // East
			{ 0, -1 } // West
	};

	public class Triple {

		public int row;
		public int col;
		public int superpowers;
		public Triple parent;

		public Triple(int row, int col, int superpowers) {
			this.row = row;
			this.col = col;
			this.superpowers = superpowers;
			this.parent = null;
		}
	}

	public  Maze maze;
	public  boolean[][] visited;
	public  int endRow, endCol;
	public ArrayList<Integer> roomTracker = new ArrayList<>();
	public boolean solved = false;
	public  Integer pathLength;
	public boolean superVisited[][][];

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
		this.maze = null;
		solved = false;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
		solved = false;
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				maze.getRoom(i,j).row = i;
				maze.getRoom(i,j).col = j;
			}
		}
	}
	public boolean canGo(int row, int col, int dir) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}

		return false;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		initialize(maze);
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}
		this.endRow = endRow;
		this.endCol = endCol;
		solve(startRow, startCol);
		return pathLength;
	}

	//BFS
	public void solve(int row, int col) {
		ArrayList<Room> frontier = new ArrayList<>();
		this.roomTracker = new ArrayList<>();
		pathLength = 0;
		this.roomTracker.add(1);
		visited[row][col] = true;
		maze.getRoom(row, col).onPath = true;
		boolean hasSolution = false;
		frontier.add(maze.getRoom(row, col));
		while (!frontier.isEmpty()) {
			ArrayList<Room> nextFrontier = new ArrayList<>();
			for (Room r : frontier) {
				if (r == (maze.getRoom(this.endRow, this.endCol))) {
					hasSolution = true;
				}
				// for each of the 4 directions
				for (int direction = 0; direction < 4; ++direction) {
					if (canGo(r.row, r.col, direction)) { // can we go in that direction?
						// yes we can :)
						Room nextRoom = maze.getRoom(r.row + DELTAS[direction][0],
								r.col + DELTAS[direction][1]);
						if (!visited[nextRoom.row][nextRoom.col]) {
							this.visited[nextRoom.row][nextRoom.col] = true;
							nextFrontier.add(nextRoom);
							nextRoom.parent = r;
						}
					}
				}
			}
			this.roomTracker.add(nextFrontier.size());
			frontier = nextFrontier;
		}
		if (hasSolution) {
			Room current = maze.getRoom(this.endRow, this.endCol);
			while (!current.onPath) {
				current.onPath = true;
				current = current.parent;
				pathLength++;
			}
			current.onPath = true;
		} else {
			pathLength = null;
		}

	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (k < 0) {
			throw new IllegalArgumentException("k cannot be negative !");
		}
		if (k >= roomTracker.size()) {
			return 0;
		} else {
			return roomTracker.get(k);
		}
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		initialize(maze);
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}
		this.endRow = endRow;
		this.endCol = endCol;
		superSolve(startRow, startCol, superpowers);
		return pathLength;
	}
	//SuperPower BFS. If wall exists, treat the next (row,col) as a different Node/Triple and BFS.
	//DuplicateCounting Exists, need to account.
	public void superSolve(int row, int col, int superpowers) {
		ArrayList<Triple> frontier = new ArrayList<>();
		this.roomTracker = new ArrayList<>();
		pathLength = 0;
		this.roomTracker.add(1);
		visited[row][col] = true;
		maze.getRoom(row, col).onPath = true;
		boolean hasSolution = false;
		frontier.add(new Triple(row, col, superpowers));
		Triple endTriple = null;
		superVisited = new boolean[maze.getRows()][maze.getColumns()][superpowers + 1];
		while (!frontier.isEmpty()) {
			int duplicateCounter = 0;
			ArrayList<Triple> nextFrontier = new ArrayList<>();
			for (Triple r : frontier) {
				if (r.row == this.endRow && r.col == this.endCol && !hasSolution) {
					hasSolution = true;
					endTriple = r;
				}
				// for each of the 4 directions
				for (int direction = 0; direction < 4; ++direction) {
					if (canGo(r.row, r.col, direction)) {
						int nextRow = r.row + DELTAS[direction][0];
						int nextCol = r.col	+ DELTAS[direction][1];
						if (!superVisited[nextRow][nextCol][r.superpowers]) {
							if (this.visited[nextRow][nextCol]) {
								duplicateCounter++;
							} else {
								this.visited[nextRow][nextCol] = true;
							}
							superVisited[nextRow][nextCol][r.superpowers] = true;
							Triple nextTriple = new Triple(nextRow,nextCol, r.superpowers);
							nextFrontier.add(nextTriple);
							nextTriple.parent = r;

						}
					} else if (canBreakWall(r.row, r.col, direction, r.superpowers)) {
							int nextRow = r.row + DELTAS[direction][0];
							int nextCol = r.col + DELTAS[direction][1];
							if (!superVisited[nextRow][nextCol][r.superpowers - 1]) {
								if (this.visited[nextRow][nextCol]) {
									duplicateCounter++;
								} else {
									this.visited[nextRow][nextCol] = true;
								}
								superVisited[nextRow][nextCol][r.superpowers - 1] = true;
								Triple nextTriple = new Triple(nextRow, nextCol, r.superpowers - 1);
								nextFrontier.add(nextTriple);
								nextTriple.parent = r;
							}
						}
					}
			}
			this.roomTracker.add(nextFrontier.size() - duplicateCounter);
			frontier = nextFrontier;
		}
		if (hasSolution) {
			Triple currTriple = endTriple;
			Room current = maze.getRoom(currTriple.row, currTriple.col);
			while (!current.onPath) {
				current.onPath = true;
				currTriple = currTriple.parent;
				current = maze.getRoom(currTriple.row, currTriple.col);
				pathLength++;
			}
			current.onPath = true;
		} else {
			pathLength = null;
		}
	}
	//Check if can pass through the wall and not the side walls.
	public boolean canBreakWall(int row, int col, int dir, int superpower) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH:
				return maze.getRoom(row, col).hasNorthWall() && superpower > 0;
			case SOUTH:
				return maze.getRoom(row, col).hasSouthWall() && superpower > 0;
			case EAST:
				return maze.getRoom(row, col).hasEastWall() && superpower > 0;
			case WEST:
				return maze.getRoom(row, col).hasWestWall() && superpower > 0;
		}

		return false;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-dense.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 3, 0, 3));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
