package me.common.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;
import me.common.helper.SecurityHelper;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.text.Collator;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StringUtils {
    // TODO: Check lại các hàm String Utils - do copy qua chưa check lại
    public static String RegexVietnameseName = "[a-zA-Z_\\u00C0-\\u01B0\\u1EA0-\\u1EFF ']+";
    public static String RegexDigits = "\\d+";
    public static String RegexDangerousText = "[<>!]+";
    public static String RegexVIBDangerousText = "[<>]+";
    public static String RegexVietnameseCompanyName = "[a-zA-Z0-9\\u00C0-\\u01B0\\u1EA0-\\u1EFF %&,()\\.\\-\\\\+\\:;\\\\/\"“”]+";

//    lazy val commonVietNameseSyllables: List[String] = {
//        val fileName = "common-vietnamese-syllables"
//        val classLoader = ClassLoader.getSystemClassLoader
//        val listSyllables = ListBuffer.empty[String]
//        var bufferedReader: Option[BufferedReader] = None
//        try {
//            val file = new File(classLoader.getResource(fileName).getFile)
//            bufferedReader = Some(new BufferedReader(new FileReader(file)))
//            bufferedReader.get.lines().forEach { s: String => listSyllables += s }
//            listSyllables.toList
//        } catch {
//            case e: Throwable =>
//                logger.info("common-vietnamese-syllables is not exist")
//                List.empty
//        } finally {
//            bufferedReader.get.close()
//        }
//    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp)
                .replaceAll("")
                // the blow 2 characters are different (use .toInt to check)
                .replace('Ð', 'Đ')
                .replace('đ', 'd')
                .replace('Đ', 'D');
    }

    public static String fixLocationName(String s) {
        return removeLocationPrefix(removeAccent(s)).toLowerCase();
    }

    public static String removeLocationPrefix(String s) {
        return removeAccent(s.replace(" ", "")
                .replace("TP.", "")
                .replace("TX.", "")
                .replace("H.", "")
        );
    }

    public static String removeLocationPrefixWithoutRemoveAccent(String s) {
        return s.replace(" ", "").replace("TP.", "").replace("TX.", "").replace("H.", "").replace("Xã", "").replace("ThịXã", "").replace("Phường", "").replace("Huyện", "").replace("ThànhPhố", "").replace("ThịTrấn", "");
    }

    public static String removeUnexpectedValue(String s, String value) {
        return s.replaceAll(value, "");
    }

    public static String cyclicRightShift(String s, int k) {
        int times = s.length() - (k % s.length());
        return s.substring(times) + s.substring(0, times);
    }

    public static String escapeNewLines(String s) {
        return s.replace("\\n", "\n");
    }

    public static String createRandomNumber(int len) {
        if (len > 18) throw new IllegalStateException("To many digits");
        long tLen = (long) (Math.pow(10, len - 1) * 9);
        long number = (long) (Math.random() * tLen) + (long) (Math.pow(10, len - 1) * 1);
        String tVal = number + "";
        if (tVal.length() != len)
            throw new IllegalStateException("The random number '" + tVal + "' is not '" + len + "' digits");
        return tVal;
    }

//    public static boolean spellCheck(String text) {
//        List<String> list = Arrays.asList(text.split(" "));
//        return list.stream().allMatch(y -> containsIgnoreCase(commonVietNameseSyllables(), removeAccent(y.trim())));
//    }

    public static boolean containsIgnoreCase(List<String> list, String text) {
        return list.stream().anyMatch(y -> y.equalsIgnoreCase(text));
    }

    public static String camelToUnderscores(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            result.append(name.substring(0, 1).toLowerCase());
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                String slc = s.toLowerCase();
                if (!s.equals(slc)) {
                    result.append("_");
                }
                result.append(slc);
            }
        }
        return result.toString();
    }

    public static String underscoreToCamel(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            boolean nextUpper = false;
            for (int i = 0; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                if ("_".equals(s)) {
                    nextUpper = true;
                } else {
                    if (nextUpper) {
                        result.append(s.toUpperCase());
                        nextUpper = false;
                    } else {
                        result.append(s.toLowerCase());
                    }
                }
            }
        }
        return result.toString();
    }

    public static String mySqlRealEscapeString(String value) {
        return value.replace("\\", "\\\\").replace("\n", "\\n").replace("\b", "\\b").replace("\r", "\\r").replace("\t", "\\t").replace("\'", "\\'").replace("\f", "\\f").replace("\"", "\\\"");
    }

    public static String removeConsecutiveSpaces(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static String formatNumberSeparator(int num) {
        return String.format("%,d", num);
    }

    public static String formatNumberSeparator(long num) {
        return String.format("%,d", num);
    }

    public static String formatNumberSeparator(double num) {
        return String.format("%,.0f", num);
    }

    public static class FullName {
        public String fullName;
        public String firstName;
        public String middleName;
        public String lastName;
    }

    public static FullName separateFullName(String name) {
        String fullName = org.apache.commons.lang3.StringUtils.normalizeSpace(name);
        int start = fullName.indexOf(' ');
        int end = fullName.lastIndexOf(' ');
        String firstName = "";
        String midName = "";
        String lastName = "";
        if (start == -1) {
            lastName = fullName;
        } else {
            firstName = fullName.substring(0, start);
            if (end > start)
                midName = fullName.substring(start + 1, end).trim();
            lastName = fullName.substring(end + 1, fullName.length()).trim();
        }
        FullName fullNameObj = new FullName();
        fullNameObj.fullName = fullName;
        fullNameObj.firstName = firstName;
        fullNameObj.middleName = midName;
        fullNameObj.lastName = lastName;
        return fullNameObj;
    }

    public static List<Long> strToListLong(String inputStr) {
        try {
            return Arrays.stream(inputStr.split(",")).filter(str -> !str.isEmpty()).map(Long::parseLong).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(String.format("got exception when parse string %s to list long, message = %s", inputStr, e.getMessage()), e);
            return new ArrayList<Long>();
        }
    }

    public static List<String> cutStringLine(String str, int lineSize) {
        String newLine = WordUtils.wrap(str, lineSize);
        return Arrays.asList(newLine.split("\n"));
    }

    public static Optional<String> convertStringToOption(String str) {
        if (str != null && !str.isEmpty()) {
            return Optional.of(str);
        } else {
            return Optional.empty();
        }
    }

    public static String convertSnakeCaseToCamelCase(String snakeCase) {
        StringBuilder camelCase = new StringBuilder(snakeCase.length());
        boolean capitalizeNext = false;

        for (int i = 0; i < snakeCase.length(); i++) {
            char c = snakeCase.charAt(i);

            if (c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    camelCase.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    camelCase.append(Character.toLowerCase(c));
                }
            }
        }

        return camelCase.toString();
    }

    public static String buildAddressDetail(Optional<String> addressNumberOpt, String addressStreet) {
        String addressNumber = addressNumberOpt.map(ele -> ele.trim() + " ").orElse("");
        return addressNumber + addressStreet.trim();
    }

    //TODO: NlpUtils

    // def formatVietnameseUserNames(name: String): String = {
    //     NlpUtils.correctVnAccentSentence(StringUtils.removeConsecutiveSpaces(name.trim.toLowerCase()))
    // }

    public static boolean isStringNumber(String str) {
        return str.matches("[0-9]+");
    }

    public static boolean compareStringVN(String str1, String str2) {
        Collator vnCollator = Collator.getInstance(Locale.forLanguageTag("vi"));
        vnCollator.setStrength(Collator.PRIMARY);
        return vnCollator.compare(str1, str2) <= 0;
    }


    public static String formatPhone(String phone) throws NumberParseException {

        final PhoneNumberUtil instance = PhoneNumberUtil.getInstance();
        final Phonenumber.PhoneNumber phoneN = instance.parse(phone, "VN");
        final boolean validNumber = instance.isValidNumber(phoneN);
        if (validNumber) {
            return instance.format(phoneN, PhoneNumberUtil.PhoneNumberFormat.E164);
        }

        return null;
    }

    public static String subString(String src, String start, String to) {
        int indexFrom = start == null ? 0 : src.indexOf(start);
        int indexTo = to == null ? src.length() : src.indexOf(to);
        if (indexFrom < 0 || indexTo < 0 || indexFrom > indexTo) {
            return null;
        }

        if (null != start) {
            indexFrom += start.length();
        }

        return src.substring(indexFrom, indexTo);
    }

    /**
     * Example: subString("abcdc","a","c",true)="bcd"
     *
     * @param src
     * @param start  null while start from index=0
     * @param to     null while to index=src.length
     * @param toLast true while to index=src.lastIndexOf(to)
     * @return
     */
    public static String subString(String src, String start, String to, boolean toLast) {
        if (!toLast) {
            return subString(src, start, to);
        }

        int indexFrom = start == null ? 0 : src.indexOf(start);
        int indexTo = to == null ? src.length() : src.lastIndexOf(to);
        if (indexFrom < 0 || indexTo < 0 || indexFrom > indexTo) {
            return null;
        }

        if (null != start) {
            indexFrom += start.length();
        }

        return src.substring(indexFrom, indexTo);
    }

    public static String join(List<?> list, String delim) {
        return join(list, delim, false);
    }

    public static String join(List<?> list, String delim, boolean ignoreEmpty) {
        int len = list.size();
        if (len == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(list.get(0).toString());
        for (int i = 1; i < len; i++) {
            String v = list.get(i).toString().trim();
            if (ignoreEmpty) {
                if (!ValidateUtils.isNullOrEmpty(v)) {
                    sb.append(delim);
                    sb.append(v);
                }
            } else {
                sb.append(delim);
                sb.append(v);
            }
        }

        return sb.toString();
    }

    public static List<Long> splitToLongList(String text, String delim) {
        return Stream.of(text.split(delim))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public static double compareByChars(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }

        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        return (longerLength - levenshteinDistance.apply(longer, shorter)) / (double) longerLength;
    }

    public static double compareByWords(String s1, String s2) {
        String a1 = stripAccents(s1.trim().toLowerCase()).replaceAll("[^A-Za-z0-9 ]", "");
        String a2 = stripAccents(s2.trim().toLowerCase()).replaceAll("[^A-Za-z0-9 ]", "");

        List<String> arr1 = Arrays.asList(a1.split(" "));
        List<String> arr2 = Arrays.asList(a2.split(" "));

        List<String> longArr = arr1;
        List<String> shortArr = arr2;
        if (arr2.size() > arr1.size()) {
            longArr = arr2;
            shortArr = arr1;
        }

        int l = longArr.size();

        Set<String> set1 = new HashSet<>(shortArr);
        Set<String> set2 = new HashSet<>(longArr);

        set2.retainAll(set1);

        if (set2.size() < shortArr.size()) {
            return 0D;
        } else {
            double i = (double) set2.size() / (double) l;
            return i;
        }

    }

    /**
     * format space : example " Nguyễn Văn A " => "Nguyễn Văn A"
     *
     * @param s
     * @return
     */
    public static String formatSpaces(String s) {
        return s.replaceAll("\\s+", " ").trim();
    }

    public static String stripAccents(String s) {
        if (!ValidateUtils.isNullOrEmpty(s)) {
            s = org.apache.commons.lang3.StringUtils.stripAccents(s);
            //format space:
            s = formatSpaces(s);
            // chuyen doi 1 so ky tu tieng viet sang normal text

            s = s.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
            s = s.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
            s = s.replaceAll("ì|í|ị|ỉ|ĩ", "i");
            s = s.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
            s = s.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
            s = s.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
            s = s.replaceAll("đ", "d");

            s = s.replaceAll("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A");
            s = s.replaceAll("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E");
            s = s.replaceAll("Ì|Í|Ị|Ỉ|Ĩ", "I");
            s = s.replaceAll("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O");
            s = s.replaceAll("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U");
            s = s.replaceAll("Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y");
            s = s.replaceAll("Đ", "D");
            return s;
        }
        return "";
    }

    public static String genarateFileName(String ext) {
        return String.format("%s.%s", SecurityHelper.md5(SecurityHelper.uid()), ext);
    }

    public static String formatNumber(double n) {
        String pattern = "###,###.###";
        final DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(n);
    }

    public static String getDefaultByObject(Object condition, String value, String defaultValue) {
        if (!ValidateUtils.isNullOrEmpty(condition)) {
            return value;
        }
        return defaultValue;
    }

    public static String getDefaultByListInteger(List<Integer> condition, String value, String defaultValue) {
        if (!ValidateUtils.isNullOrEmpty(condition)) {
            return value;
        }
        return defaultValue;
    }

    public static boolean containsSubStringIgnoreCase(String source, String subString) {
        return Pattern.compile(Pattern.quote(subString), Pattern.CASE_INSENSITIVE).matcher(source).find();
    }

    public static boolean containsInsensitiveCase(List<String> source, String subString) {
        return source.stream().anyMatch(x -> x.equalsIgnoreCase(subString));
    }

    public static List<Integer> splitStringIntoList(String string) {
        try {
            if (ValidateUtils.notNullOrEmpty(string)) {
                int[] ints = Arrays.stream(string.trim().split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                return Arrays.stream(ints).boxed().collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public static List<Long> splitStringIntoLongList(String string) {
        try {
            if (ValidateUtils.notNullOrEmpty(string)) {
                long[] longs = Arrays.stream(string.trim().split(","))
                        .mapToLong(Long::parseLong)
                        .toArray();
                return Arrays.stream(longs).boxed().collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public static String limitString(String str, int maxLength) {
        return limitString(str, maxLength, "");
    }
    public static String limitStringWithDot(String str, int maxLength) {
        return limitString(str, maxLength, "...");
    }

    public static String limitString(String str, int maxLength, String ellipsis) {
        if (str.length() > maxLength) {
            return str.substring(0, maxLength) + (ellipsis != null ? ellipsis : "");
        } else {
            return str;
        }
    }
    public static Function<Void, String> getRandomString = (nothing) -> UUID.randomUUID().toString().replaceAll("-","");

    public static boolean validateRegex(String inputStr, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
}
