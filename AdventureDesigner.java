
import java.util.Scanner;

public class AdventureDesigner {

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);

		boolean start = true;// For the continuation of the program
		String choice = "";

		// initializes the "Root" of the tree
		System.out.println("Please Enter a title");
		String title = stdin.nextLine();
		System.out.println("Please Enter a scene");
		String scene = stdin.nextLine();// SceneDescription

		SceneNode root = new SceneNode(title, scene); // new SceneNode(String title, String SceneDescription)
		SceneTree storyTree = new SceneTree(root);

		System.out.println("Scene " + SceneNode.getNumScenes() + " Added");

		SceneNode cursor = storyTree.getCursor();
		SceneNode temp;// temp Node;

		while (start) {
			printMenu();

			choice = stdin.nextLine().toUpperCase();
			switch (choice) {
			case "A":
				System.out.println("Please Enter a title");
				title = stdin.nextLine();
				System.out.println("Please Enter a scene");
				scene = stdin.nextLine();
				temp = new SceneNode(title, scene);
				try {
					temp.setParent(cursor);
					cursor.addSceneNode(temp);
					System.out.println("Scene " + SceneNode.getNumScenes() + " Added");
				} catch (FullSceneException ex) {
					System.out.println("You cannot add another scene!");
				}
				break;
			case "R":// Remove Scene
				System.out.println("Please Enter an option ");
				String optionToRemove = stdin.nextLine().toUpperCase();
				try {
					storyTree.removeScene(optionToRemove);
				} catch (NoSuchNodeException ex) {
					System.out.println("There is no such scene!");
				}
				break;
			case "S":// Show Current Scene
				cursor.displayFullScene();
				break;
			case "P": // prints adventure tree
				printEntireTree(root, cursor.getSceneID(), 0);
				break;
			case "B":// Go back a scene
				try {
					storyTree.moveCursorBackwards();
					System.out.println("Successfully moved back to " + storyTree.getCursor().getTitle());
				} catch (NoSuchNodeException ex) {
					System.out.println("There is no node perceding the cursor!");
				}
				cursor = storyTree.getCursor();
				break;
			case "F":// Go forward (go to a child)
				System.out.println("Which option do you wish to move to: ");
				String option = stdin.nextLine().toUpperCase();
				try {
					storyTree.moveCursorForward(option);
					cursor = storyTree.getCursor();
					System.out.println("Successfully moved to " + cursor.getTitle());
				} catch (NoSuchNodeException ex) {
					System.out.println("Option does not exist!");
				}
				break;

			case "G":// plays game // uses displayScene()
				playGame(root);
				System.out.println("Game Ending...");
				break;

			case "N":// Print path to Cursor. Prints from the cursor to the root
				String path = storyTree.getPathFromRoot();
				System.out.println(path);
				break;

			case "M":// Mode Scene
				System.out.println("Move current scene to: ");
				int sceneNum = Integer.parseInt(stdin.nextLine().toUpperCase());
				try {
					SceneNode tempParent = cursor.getParent();
					String tempLetter = cursor.getLetter();
					storyTree.moveScene(sceneNum);
					tempParent.removeScene(tempLetter);
				} catch (NoSuchNodeException ex) {
					System.out.println("This Scene Number does not exist");
				} catch (FullSceneException ex1) {
					System.out.println("The scene that you are trying to move to is full");
				}
				break;

			case "Q":
				start = false;
				break;

			default:
				System.out.println("Please choose an option from the menu");
				break;
			}
		}

		System.out.println("Program terminating normally...");
		stdin.close();// closes the Scanner
	}

	public static void playGame(SceneNode root) {
		System.out.println("Now starting game...");
		SceneNode currentScene = root;
		Scanner stdin2 = new Scanner(System.in);
		currentScene.displayScene();
		while (!currentScene.isEnding()) {
			System.out.println(currentScene.getLeft().getLetter() + ") " + currentScene.getLeft());
			if (currentScene.getMiddle() != null) {
				System.out.println(currentScene.getMiddle().getLetter() + ") " + currentScene.getRight());
			}
			if (currentScene.getRight() != null) {
				System.out.println(currentScene.getRight().getLetter() + ") " + currentScene.getRight());
			}
			System.out.println("Which option do you wish to move to: ");

			String option = stdin2.nextLine().toUpperCase();

			switch (option) {
			case "A":
				currentScene = currentScene.getLeft();
				currentScene.displayScene();
				break;
			case "B":
				currentScene = currentScene.getMiddle();
				currentScene.displayScene();
				break;
			case "C":
				currentScene = currentScene.getRight();
				currentScene.displayScene();
				break;
			default:
				System.out.println("Please Enter A valid Choice (A,B,or C) ");
				break;
			}

		}
	}

	public static void printEntireTree(SceneNode node, int sceneID, int iteration) {
		if (node == null) {
			return;
		}

		if (node.getSceneID() == 1) {
			if (node.getSceneID() == sceneID) {
				indenting(iteration);
				System.out.println(node + "*");
			} else {
				indenting(iteration);
				System.out.println(node);
			}

		} else {
			if (node.getSceneID() == sceneID) {
				indenting(iteration);
				System.out.println(node.getLetter() + ")" + node + "*");
			} else {
				indenting(iteration);
				System.out.println(node.getLetter() + ")" + node);
			}
		}

		while (!node.isEnding()) {
			iteration = iteration + 1;
			printEntireTree(node.getLeft(), sceneID, iteration);
			printEntireTree(node.getMiddle(), sceneID, iteration);
			printEntireTree(node.getRight(), sceneID, iteration);
			break;
		}
	}

	public static void indenting(int iterations) {
		for (int i = 0; i < iterations; i++) {
			System.out.print("\t");
		}
	}

	public static void printMenu() { // Prints the Menu
		System.out.println("\n");
		System.out.println("A) Add Scene\r\n" + "R) Remove Scene\r\n" + "S) Show Current Scene\r\n"
				+ "P) Print Adventure Tree\r\n" + "B) Go Back A Scene\r\n" + "F) Go Forward A Scene\r\n"
				+ "G) Play Game\r\n" + "N) Print Path To Cursor\r\n" + "M) Move Scene\r\n" + "Q) Quit");
		System.out.println("Please choose from the above menu");
	}

}
