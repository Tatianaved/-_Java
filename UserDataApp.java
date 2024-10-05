import java.io.*;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserDataApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные через пробел: Фамилия Имя Отчество дата_рождения номер_телефона пол");
        String input = scanner.nextLine();
        
        // Разделение строки по пробелам
        String[] parts = input.split(" ");
        
        // Проверка количества данных
        if (parts.length != 6) {
            if (parts.length < 6) {
                System.out.println("Ошибка: введено меньше данных, чем требуется.");
            } else {
                System.out.println("Ошибка: введено больше данных, чем требуется.");
            }
            return;
        }
        
        // Извлечение данных
        String lastName = parts[0];
        String firstName = parts[1];
        String middleName = parts[2];
        String birthDate = parts[3];
        String phoneNumber = parts[4];
        String gender = parts[5];
        
        try {
            // Проверка формата даты рождения
            validateBirthDate(birthDate);
            
            // Проверка формата номера телефона
            long phone = validatePhoneNumber(phoneNumber);
            
            // Проверка пола
            validateGender(gender);
            
            // Запись данных в файл
            writeToFile(lastName, firstName, middleName, birthDate, phone, gender);

        } catch (ParseException e) {
            System.out.println("Ошибка: неверный формат даты рождения. Ожидается формат dd.mm.yyyy.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат номера телефона. Ожидается целое число.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: неверный пол. Ожидается 'm' или 'f'.");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл.");
            e.printStackTrace();
        }
    }
    
    // Метод для проверки формата даты
    private static void validateBirthDate(String birthDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        dateFormat.parse(birthDate); // Если дата неверная, выбросится ParseException
    }
    
    // Метод для проверки формата номера телефона
    private static long validatePhoneNumber(String phoneNumber) throws NumberFormatException {
        return Long.parseUnsignedLong(phoneNumber);
    }
    
    // Метод для проверки пола
    private static void validateGender(String gender) throws IllegalArgumentException {
        if (!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f")) {
            throw new IllegalArgumentException("Неверный пол.");
        }
    }
    
    // Метод для записи данных в файл
    private static void writeToFile(String lastName, String firstName, String middleName, 
                                    String birthDate, long phone, String gender) throws IOException {
        String fileName = lastName + ".txt";
        try (FileWriter fileWriter = new FileWriter(fileName, true); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(lastName + " " + firstName + " " + middleName + " " + birthDate + " " + phone + " " + gender);
            bufferedWriter.newLine();
            System.out.println("Данные успешно записаны в файл: " + fileName);
        }
    }
}
