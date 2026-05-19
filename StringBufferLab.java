import java.util.ArrayList;
import java.util.List;

public class StringBufferLab {

    public static List<StringBuffer> splitIntoSentences(StringBuffer text) {
        if (text == null) {
            throw new IllegalArgumentException("Текст не може бути null.");
        }
        if (text.length() == 0) {
            throw new IllegalArgumentException("Текст не може бути порожнім.");
        }

        List<StringBuffer> sentences = new ArrayList<>();
        StringBuffer current = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '.' || ch == '!' || ch == '?') {
                trimStringBuffer(current);
                if (current.length() > 0) {
                    sentences.add(current);
                }
                current = new StringBuffer();
            } else {
                current.append(ch);
            }
        }

        trimStringBuffer(current);
        if (current.length() > 0) {
            sentences.add(current);
        }

        return sentences;
    }

    public static List<StringBuffer> splitIntoWords(StringBuffer sentence) {
        List<StringBuffer> words = new ArrayList<>();
        StringBuffer word = new StringBuffer();

        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (Character.isLetter(ch) || ch == '\'' || ch == '\u2019') {
                word.append(Character.toLowerCase(ch));
            } else {
                if (word.length() > 0) {
                    words.add(word);
                    word = new StringBuffer();
                }
            }
        }
        if (word.length() > 0) {
            words.add(word);
        }

        return words;
    }

    public static boolean areEqual(StringBuffer sb1, StringBuffer sb2) {
        if (sb1.length() != sb2.length()) {
            return false;
        }
        for (int i = 0; i < sb1.length(); i++) {
            if (sb1.charAt(i) != sb2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsWord(StringBuffer word, StringBuffer sentence) {
        List<StringBuffer> words = splitIntoWords(sentence);
        for (StringBuffer w : words) {
            if (areEqual(w, word)) {
                return true;
            }
        }
        return false;
    }

    public static List<StringBuffer> findUniqueWordsInFirstSentence(StringBuffer text) {
        List<StringBuffer> sentences = splitIntoSentences(text);

        if (sentences.size() < 2) {
            throw new IllegalStateException(
                "Текст повинен містити щонайменше два речення для виконання завдання."
            );
        }

        StringBuffer firstSentence = sentences.get(0);
        List<StringBuffer> firstWords = splitIntoWords(firstSentence);
        List<StringBuffer> result = new ArrayList<>();

        for (StringBuffer word : firstWords) {
            boolean foundInOther = false;
            for (int i = 1; i < sentences.size(); i++) {
                if (containsWord(word, sentences.get(i))) {
                    foundInOther = true;
                    break;
                }
            }
            if (!foundInOther) {
                boolean alreadyAdded = false;
                for (StringBuffer r : result) {
                    if (areEqual(r, word)) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    result.add(word);
                }
            }
        }

        return result;
    }

    private static void trimStringBuffer(StringBuffer sb) {
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
       String inputText = "Палац «Ластівчине гніздо» – пам’ятка відома далеко за межами України. "
        + "відома Казковий палац, що, здається, дивом тримається на краєчку стрімкої кручі, давно став символом Криму, його візитівкою. "
        + "Палац розташований на прямовисній скелі Аврора, що є відногою мису Ай-Тодор. "
        + "До речі, сама ідея збудувати палац на цьому місці, над урвищем, є результатом сміливої фантазії та великої жаги прекрасного. "
        + "Зовні палац нагадує маленький лицарський замок із дитячих казок. "
        + "На краю скелі височіє двоповерхова вежа зі стрілкуватими вікнами та короною зубців-мерлонів. "
        + "Навколо вежі влаштований балкон – оглядовий майданчик, з якого розгортається чудовий морський краєвид та видно Ялту. "
        + "Будівля на скелі Аврора виникла наприкінці ХІХ століття. "
        + "Спочатку це була дерев’яна дача. "
        + "Скромний, не знаний ніким будиночок перетворив на замок нафтовий барон Штейнгель. "
        + "Автор цього чуда Леонід Шервуд – архітектор, інженер і скульптор. "
        + "У 1927 році в Криму стався сильний землетрус, епіцентр якого був у морі біля Ялти. "
        + "Палац не постраждав дивом, проте частину скелі Аврора забрало море. "
        + "Реконструкцію провели лише через п’ятдесят років. "
        + "Тут проходили зйомки відомих фільмів, а саме: «Десять негренят», «Синій птах», «Академія пана Клякси» тощо.";
        
        StringBuffer textBuffer = new StringBuffer(inputText);

        try {
            System.out.println("Лабораторна робота №2");
            System.out.println("Завдання: знайти слово в першому реченні, якого немає в жодному з наступних.\n");

            List<StringBuffer> uniqueWords = findUniqueWordsInFirstSentence(textBuffer);

            System.out.println("Слова з першого речення, яких немає в наступних:");
            if (uniqueWords.isEmpty()) {
                System.out.println("  (таких слів не знайдено)");
            } else {
                for (StringBuffer word : uniqueWords) {
                    System.out.println("  -> " + word.toString());
                }
            }

        } catch (Exception e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}