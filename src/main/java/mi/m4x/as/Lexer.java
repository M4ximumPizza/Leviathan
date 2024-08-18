package mi.m4x.as;

import java.io.*;
import java.util.*;

public class Lexer {
    private BufferedReader reader;
    private int currentChar;
    private int line = 1;
    private int column = 0;

    public Lexer(String filePath) throws IOException {
        reader = new BufferedReader(new FileReader(filePath));
        advance();
    }

    private void advance() throws IOException {
        currentChar = reader.read();
        column++;
    }

    private char peek() throws IOException {
        reader.mark(1);
        int nextChar = reader.read();
        reader.reset();
        return (char) nextChar;
    }

    public List<Token> lex() throws IOException {
        List<Token> tokens = new ArrayList<>();
        while (currentChar != -1) {
            if (Character.isWhitespace(currentChar)) {
                if (currentChar == '\n') {
                    line++;
                    column = 0;
                }
                advance();
                continue;
            }

            if (Character.isDigit(currentChar)) {
                tokens.add(lexNumber());
            } else if (Character.isLetter(currentChar)) {
                tokens.add(lexIdentifier());
            } else {
                switch (currentChar) {
                    case ';':
                        while (currentChar != -1 && currentChar != '\n') {
                            advance();
                        }
                        break;
                    case ':':
                        tokens.add(new Token(TokenType.COLON, ":", line, column));
                        advance();
                        break;
                    case ',':
                        tokens.add(new Token(TokenType.COMMA, ",", line, column));
                        advance();
                        break;
                    case '+':
                        tokens.add(new Token(TokenType.PLUS, "+", line, column));
                        advance();
                        break;
                    case '-':
                        tokens.add(new Token(TokenType.MINUS, "-", line, column));
                        advance();
                        break;
                    case '*':
                        tokens.add(new Token(TokenType.MULTIPLY, "*", line, column));
                        advance();
                        break;
                    case '/':
                        tokens.add(new Token(TokenType.DIVIDE, "/", line, column));
                        advance();
                        break;
                    case '(':
                        tokens.add(new Token(TokenType.LEFT_PAREN, "(", line, column));
                        advance();
                        break;
                    case ')':
                        tokens.add(new Token(TokenType.RIGHT_PAREN, ")", line, column));
                        advance();
                        break;
                    default:
                        throw new RuntimeException("Unexpected character: " + (char) currentChar);
                }
            }
        }

        tokens.add(new Token(TokenType.EOF, "", line, column));
        return tokens;
    }

    private Token lexNumber() throws IOException {
        StringBuilder number = new StringBuilder();
        int startColumn = column;

        while (currentChar != -1 && Character.isDigit(currentChar)) {
            number.append((char) currentChar);
            advance();
        }

        return new Token(TokenType.NUMBER, number.toString(), line, startColumn);
    }

    private Token lexIdentifier() throws IOException {
        StringBuilder identifier = new StringBuilder();
        int startColumn = column;

        while (currentChar != -1 && (Character.isLetterOrDigit(currentChar) || currentChar == '_')) {
            identifier.append((char) currentChar);
            advance();
        }

        String idStr = identifier.toString().toUpperCase();

        // Check if the identifier matches an instruction
        if (Main.getInstruction(idStr) != null) {
            return new Token(TokenType.valueOf(idStr), idStr, line, startColumn);
        }


        return new Token(TokenType.IDENTIFIER, idStr, line, startColumn);
    }

    public Token nextToken() throws IOException {
        advance();
        return lexIdentifier();
    }
}
