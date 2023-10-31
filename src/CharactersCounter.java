import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Программа, подсчитывающая количество каждого символа в input.txt
 * @author Daniil Kodintsev
 */
public class CharactersCounter {
    public static void main(String[] args) {

        // Мап для хранения каждого встречающегося символа
        // Удобно тем, сам символ можемт выстпать в роли ключа. Удобно смотреть, встречался ли уже такой символ
        // Если не встречался, просто добавляем его в мап
        HashMap<Character,Integer> characters = new HashMap<>();

        // Считываем файл с помощью Scanner
        try(Scanner input = new Scanner(new File("resources/input.txt"))){
            while(input.hasNext()){
                String inputLine = input.nextLine();

                // Убираем все служебные знаки, в том числе пробелы. Они нам не нужны
                inputLine = inputLine.replaceAll("\\s+","");

                // Для каждого символа из каждой строки смотрим, есть ли уже такой символ в мапе
                // Если его нет - добавляем
                // Если есть - увеличиваем соответсвующее ему число на 1
                // Таким образом считаем количество встреченных символов
                for(Character character : inputLine.toCharArray()){
                    if(!characters.containsKey(character)){
                        characters.put(character, 1);
                    } else {
                        characters.put(character, characters.get(character) + 1);
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }


        // Выводим информацию о каждом символе в файл output.txt
        try(FileWriter output = new FileWriter("resources/output.txt", StandardCharsets.UTF_8)){
            output.write("В данном файле программа выводит количество\nкаждого встреченного в файле input.txt символа:\n\n");

            // Сортируем символы по их количеству и выводим информацию о каждом в файл
            characters.entrySet().stream()
                    .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                    .forEach(entry -> {
                        try {
                            output.write("'" + entry.getKey() + "'" + "=" + entry.getValue() + ", ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}