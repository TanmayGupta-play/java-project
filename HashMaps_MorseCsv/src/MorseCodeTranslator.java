    import java.io.*;
    import java.util.HashMap;
    import java.util.Map;
    import javax.swing.JFileChooser;

    class MorseCodeTranslator {
        private Map<Character, String> morseMap = new HashMap<>();
        private Map<String, Character> reverseMorseMap = new HashMap<>();

        // Constructor to initialize the mappings from the file
        public MorseCodeTranslator(String filePath) {
            loadMorseCode(filePath);
        }

        // Loads Morse code mappings from the file
        private void loadMorseCode(String filePath) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 1) {
                        char letter = parts[0].charAt(0);
                        String morseCode = parts[1];
                        morseMap.put(letter, morseCode);
                        reverseMorseMap.put(morseCode, letter);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading Morse code file: " + e.getMessage());
            }
        }

        // Encodes a text message into Morse code
        public String encode(String message) {
            StringBuilder encodedMessage = new StringBuilder();
            for (char c : message.toUpperCase().toCharArray()) {
                if (morseMap.containsKey(c)) {
                    encodedMessage.append(morseMap.get(c)).append(" ");
                } else if (c == ' ') {
                    encodedMessage.append(" / ");  // Separator for spaces
                } else {
                    System.out.println("Character " + c + " not found in Morse code dictionary.");
                }
            }
            return encodedMessage.toString().trim();
        }

        // Decodes a Morse code message into text
        public String decode(String morseMessage) {
            StringBuilder decodedMessage = new StringBuilder();
            String[] words = morseMessage.split(" / ");
            for (String word : words) {
                String[] letters = word.trim().split(" ");
                for (String morse : letters) {
                    if (reverseMorseMap.containsKey(morse)) {
                        decodedMessage.append(reverseMorseMap.get(morse));
                    }
                }
                decodedMessage.append(" ");
            }
            return decodedMessage.toString().trim();
        }



            public static void main(String[] args) {
                // File chooser to select the Morse code CSV file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Morse Code Dataset File");
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // Initialize the MorseCodeTranslator with the selected file
                    MorseCodeTranslator translator = new MorseCodeTranslator(selectedFile.getAbsolutePath());

                    // Test encoding and decoding
                    String message = "Yash birje";
                    String encoded = translator.encode(message);
                    System.out.println("Encoded: " + encoded);

                    String decoded = translator.decode(encoded);
                    System.out.println("Decoded: " + decoded);
                } else {
                    System.out.println("No file selected.");
                }
            }
        }
