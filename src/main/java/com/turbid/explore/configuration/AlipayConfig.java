package com.turbid.explore.configuration;

public class AlipayConfig {
    // 商户appid
    public static String APPID = "2021001155653377";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCjX0qfsBPOgh9Da1KnAnlAY9r0fieiTHFaYznEjJ2g/b6Ood25NFZE4E7ogEBn6G6U8LZ505RBOvik2VjtNED/7PieGy80A4N771YbkHX3CgctafgVfD2EYJurUC4RW4agwDQXzrqhd3vshWrdGhEwvZaoo6I+2nAuLp9RJdnV6sYQZk+woQNDq9zyDJZ+aH45nfbb6Zu+leNKX90kFG2pE7/x76vVylnc4U2z57dKGRscgqSTST4cte2Witihl+PlUNT2/slUIH3wSoixeGm1vT6WFKGuQ1ME5b5wmgykrlSxHRRf9fYlcDGpfVWsiMLb+L3FJSd4OtrHbKaR3BVDAgMBAAECggEAc7OzF/ENtf01wvupJ5p64P1XPjxyOWFGDiuj5+1mXXQXltBQRTApwNHTUJo0AuL8HgN0WEdeAeyP18AOWL/fIOFlGiRKg93RKt7P7umaggDaIHGiPyUkNDJvTR6V4bkeWY6F1i6UwnHqlBHRIxGpBwneP0TUTQtK04o1TrUt128ydPX/9ZWxAPU4EqMG7SSAhsU4+DsxrEGXusrInxBMa9SRJIZQ4EKcuE3MtjvHRFJv89Z5VKN1s+xpifV7wweYWTO3YIDQ/ahJOZGrSnd4oVnUijBjFtvQEO86F8ETqHm/c1KDRLMsEVsav1xS7/G+VNSUVu1e22JyYfr6nEdgUQKBgQDNzLRxdsgkJEz0tiM/gMvZJtOlWrIwhFCLtXd8ot/vIWSjR51BmhRwgnVb1M54lU3HZrq1BdzfaUxr6Mwp8Hr/Deuuz5khc2JLJ3AsJn4Uf51SSf5oF488kTWBIeX5MXD/fLQPIpemlptpseYKZ+7PHvQn12WK1Op9Wrd/4RJnJwKBgQDLOS35eac9OcoDlMAV8GOBWPX7Krs4OPv5VyQ2k31Yyq6LRMh8FunaEewLQ9fr1B/VzIkx9C9o7hu3wyw5KcDMWQs975QRDq5A5Owd7earVpCVtqz94ZbIqsgr4kK+wWM211GK/FwAQ1l8PyqO66oZpjYkZhbFLFuYj2/taO5ShQKBgQCd2hHdpdpFvoI4gSvwHX4+BYX5IuPiStrtDjekJdnVtatXgiqgkupykd4qncPwwbRy6dzz1Jdh1YaEjn9q+8WbtLW91AYEnlgahRmJA81ZM7kb0R2OAeQf9NExRyr6RG4DyUbjBQmFAagbHhTwjl+iBoAHvzNnlYB2vgpZz+0pqQKBgQDCTVVLpajkvRANBHfzgfdhgHEPR/fg9XrVFyAknjfGYWMo/QbCvhWwzlnlG2SishoODJVQEawSozYVKKJrtSQFPU2mVh43gNXSbg3jP8bAIu/ydMpJoxrLmZ3xd09hmv0FPjFUtZwxFDPGu44xP9lO1MAHZSR4OMUKxHm7gAv9EQKBgDsjEfyS9feKfv1YiFxr9Jm6RiDfn9pJ+yFUw1ezu/cHf9TZWJrYg3KoTpW+TVRSYKRb10m5V5AYzRSS5mSaZm5RkMIAJhafvsanEzX2M0YwY6wYE83R1Yshdj2S4QZLiPcvwQ4ISKuaJ7NREpK5KzFELxy5qa8Hwnh+IA8gvqze";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "https://www.deslibs.com/pay/ali/asyncnotify";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "https://www.deslibs.com/pay/ali/notifiy";
    // 请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhPFv0KYtMmeUGSxcggDJwSMAkKmJ+LQSb84ST8c1um0NNKlnddtPn3tYxJde0UiCjB5jGoV7K+p7XXdDD+qLit2BRo9RopnrVFtMUMrqADwOY79ie6b7ohiL8RVU7kxIgXXR54bw0637T4V05eQSKaUeLbaUUqTLZ59uTIx2HN31r3sCWY89mR5eiuJLUnUSVnrC5WP/Z17FMp76rlK5VC+iOKY6RXqL/uKiv+k3IliMZzMpfhwCuB6pzKRTj4NBtYyUlsunPSEDM0+DwskhjIoPYE1xoxfLV1jgu5crvi0BkyXuUN9FQdLFkvMdB8y7DBlzionUG7VY/zLHaiqHEQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";


    // 商户appid
    public static String testAPPID = "2016102300743888";
    // 私钥 pkcs8格式的
    public static String testRSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDe/SQQj1/K1hS6UI4WJMOq5zrS02lHmxOxHCTbt3QnXNLjAGHY6MiuQqSQXlZIweXBKsSkZ/UBejcv8VnciuDEraAqEOnCpJBJ31pYxGpmbeiMxPU+OkekPvXRzHO2nFsJT7sATs9wGYaNAoE4CW5HyiZFBiHDIAbUTmiz37OB7DhERGsx5SeRufpXjdg3PYaPYEJ97YeELf51ZIf0qQwZNFLysupZ56SfqoIv1VReL0ypMKZy+CAXRAVhi8Bz7TvuPR7KWGtYNHkSoVxBtxuG05veDyALpPYsW7DGEnzIN0gBShcXWlmOuYfqHkOhLO6WkJUDuthZeK9FtvIvwXMnAgMBAAECggEAFWbSWUyJcxxM7PziRjnNFdAPk0B7MEYwA6Wh2/ylh0mfa+4TdhARgrget0R2n5EMjheeu/DGURf7x/wwFby2ienoH2F0LhxBRPXM5Awl8iLGWQxmRUyup5LrYPFwgaxMCH7Age0Od8Hbahk9cbVmAjnipmlKQEIIpcPIxMrJncRySKv6fsl5dFTfC1O6enwjEBLyGHu2qrto19I0MVMqnAxC8J0hp1u17SazxMDhG/6XzvHNvVmEwft3asZdvrfanQ9QM1K/mmbRQNop4RnopwYfdiz3KTLpX/RZLy7JegPhPwSyFamxV8qODm+bvm8E+uOmw0qIcQrGyqA6oBdsYQKBgQD9cts9OyaPlzv5xcvCrWWESNH2yLwMEQL9c2n2rv5J1+jjSz9/QFKTWVWcrpK7W8xDc6yPms4Ram69bbjgsUT7CuYHhndC0ZQQ7YqIghiO+2UJPLHkAx8J54Ob79YNcPRQrKnjW6IYJJLfOMjDfCUzt7fIr8R7MTfPtjq4bHPzjwKBgQDhO8nqXxls2EqVW9cspag+/Z84RtLuXgBJZx18soYM7hHD8qkhfsmEgC5GotoBaKLcYIKtd5LlGbxlvBL+z4jcUrJzYgo6JIYMLWpk6bYVVqdemqwOR8Rp/sVQMiMC91F8N/nHDQvkjzEVkHZg4r2ElL9/ST8QakzDqjlYmZ/a6QKBgHzP3SuwP3MjFQQR5iA+5+lXOx7/B6whjIloFtzGnK+cxjF40xJFiOTLb5+zUMs3j8X/5xDpOWXWNibPK+ZrJf9DBLjb/UtIjlQvxrMm7hUrtDvySeBxsQ1dl7BpUc5Yu9gmz5sJey/Mgw+ulR+/uUpYxQbtmCLawBNoOE6uCgP9AoGBAMiudfz+mO/TU7j6qKEeROdDIU+KQzyhws2kM7C8fBYfNPMLTkkK59DISPMk/OuOaLXExAp9ZQP+R0+6II6rKX1trZSDASvHCSzmdkrNtjATEnOHis/6f/YVS4dl9K4a4J62XTZO2I7gStK08dTnkgcEZg+qmqcsn5MjI49e6gCpAoGAcS7UCfareWuPt4qQRbbJZ84Iuy6lvJhbZ8rs+siD6PYNhGSA2CwwAMY6IqE4eT+xvH2mQMRiX/8pU3zy/d7s/jJtrkNe9UeMLSjHjY3+IPE//+BnGAUlbpcs7ndhJZaXSPjtakyha088ZCBln96/SUAUUqg1xZVJYs90orsy25Y=";
    // 支付宝公钥
    public static String testALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkbOoSI8qiFTKzJcHB+vL/iOAeraSqq9AZNlQQFsngtf+VVSLXMjxII6njWnYWnehTKUeFdRIalZ2wJPv3DUyjdsHg0EoBqNx7616hT0RbVc7h52/sV8wDrRGWN6FX6XtYzgVRDp95y9gPBDrinLHQZPDRhg9ufSmGYbHVrb4Dm/oT1h8kk+uAvpFGGGViNGvEXaqzVXDZnMPqmVTcnN+o6FEjgFl/mQXiLv0wTIM+nJr2u1+epmdGb+SLmpJ3gNcDoR0u+S8KwsJ4bRHAHuNR8AjYrCaeIzOf32m3mbaARxSUJ+4K30QIGXZ6YJyi/2/HciML3dvnlRTh1A2UQ5trQIDAQAB";

}
