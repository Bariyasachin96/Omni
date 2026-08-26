import java.util.*;
public class Iso {
    static Map<String,String> to3 = new HashMap<>(), to2 = new HashMap<>();
    static String[][] iso2Term = {{"sq","sqi"},{"hy","hye"},{"eu","eus"},{"my","mya"},{"zh","zho"},{"cs","ces"},{"nl","nld"},{"fr","fra"},{"ka","kat"},{"de","deu"},{"el","ell"},{"is","isl"},{"mk","mkd"},{"mi","mri"},{"ms","msa"},{"fa","fas"},{"ro","ron"},{"sk","slk"},{"bo","bod"},{"cy","cym"}};
    static String[] noIso2 = {"fil","ceb","haw","hmn","war","nso","syr","chr","lus","sco"};
    static { for (String i2 : Locale.getISOLanguages()) { String i3=""; try{i3=new Locale(i2).getISO3Language();}catch(Exception e){}
            if(i3.isEmpty()) continue; to3.put(i2,i3); if(!to2.containsKey(i3)) to2.put(i3,i2); }
        for(String[] p: iso2Term){ to3.put(p[0],p[1]); to2.put(p[1],p[0]); }
        to3.put("iw","heb"); to3.put("he","heb"); to3.put("in","ind"); to3.put("id","ind"); to3.put("ji","yid"); to3.put("yi","yid");
        for(String c: noIso2){ to2.put(c,c); to3.put(c,c); } }
    static String norm(String t){ int cut=t.length(); for(int i=0;i<t.length();i++){char c=t.charAt(i); if(c=='-'||c=='_'){cut=i;break;}} return t.substring(0,cut).toLowerCase(Locale.ROOT); }
    static String toIso3(String tag){ String n=norm(tag); String m=to3.get(n); if(m!=null) return m;
        if(n.length()==3){ String i2=to2.get(n); if(i2==null) return null; String r=to3.get(i2); return r!=null?r:n; } return null; }
    public static void main(String[] a){
        String tags="af am ar az be bg bg-Latn bn bs ca ceb co cs cy da de el el-Latn en eo es et eu fa fi fil fr fy ga gd gl gu ha haw hi hi-Latn hmn hr ht hu hy id ig is it iw ja ja-Latn jv ka kk km kn ko ku ky la lb lo lt lv mg mi mk ml mn mr ms mt my ne nl no ny pa pl ps pt ro ru ru-Latn sd si sk sl sm sn so sq sr st su sv sw ta te tg th tr uk ur uz vi xh yi yo zh zh-Latn zu und";
        List<String> bad=new ArrayList<>();
        for(String t: tags.split(" ")) if(toIso3(t)==null) bad.add(t);
        System.out.println("CLD3 tags with no iso3: "+bad);
        System.out.println("und -> "+toIso3("und"));
    }
}
