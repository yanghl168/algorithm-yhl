package class13;

import javax.xml.soap.Node;

public class Test {

    //正则表达式校验身份证号码，考虑15位和18位
    public static boolean isIDCard(String idCard) {
        String regex = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
        return idCard.matches(regex);
    }




    public static void main(String[] args) {
        String idCard = "12345678901234576X";
        System.out.println(isIDCard(idCard));
    }
}
