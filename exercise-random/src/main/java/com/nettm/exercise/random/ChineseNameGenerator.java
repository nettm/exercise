package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChineseNameGenerator extends BaseDataGenerator {

	protected final static int MIN_LENGTH = 2;

	protected final static int MAX_LENGTH = 3;

	protected final static String[] LAST_NAME = new String[86];// 姓

	protected final static String[] FIRST_NAME = new String[79];// 名

	static  {
		LAST_NAME[0] = "白";

		LAST_NAME[1] = "白";

		LAST_NAME[2] = "蔡";

		LAST_NAME[3] = "曹";

		LAST_NAME[4] = "陈";

		LAST_NAME[5] = "戴";

		LAST_NAME[6] = "窦";

		LAST_NAME[7] = "邓";

		LAST_NAME[8] = "狄";

		LAST_NAME[9] = "杜";

		LAST_NAME[10] = "段";

		LAST_NAME[11] = "范";

		LAST_NAME[12] = "樊";

		LAST_NAME[13] = "房";

		LAST_NAME[14] = "风";

		LAST_NAME[15] = "符";

		LAST_NAME[16] = "福";

		LAST_NAME[17] = "高";

		LAST_NAME[18] = "古";

		LAST_NAME[19] = "关";

		LAST_NAME[20] = "郭";

		LAST_NAME[21] = "毛";

		LAST_NAME[22] = "韩";

		LAST_NAME[23] = "胡";

		LAST_NAME[24] = "花";

		LAST_NAME[25] = "洪";

		LAST_NAME[26] = "侯";

		LAST_NAME[27] = "黄";

		LAST_NAME[28] = "贾";

		LAST_NAME[29] = "蒋";

		LAST_NAME[30] = "金";

		LAST_NAME[31] = "廖";

		LAST_NAME[32] = "梁";

		LAST_NAME[33] = "李";

		LAST_NAME[34] = "林";

		LAST_NAME[35] = "刘";

		LAST_NAME[36] = "龙";

		LAST_NAME[37] = "陆";

		LAST_NAME[38] = "卢";

		LAST_NAME[39] = "罗";

		LAST_NAME[40] = "马";

		LAST_NAME[41] = "牛";

		LAST_NAME[42] = "庞";

		LAST_NAME[43] = "裴";

		LAST_NAME[44] = "彭";

		LAST_NAME[45] = "戚";

		LAST_NAME[46] = "齐";

		LAST_NAME[47] = "钱";

		LAST_NAME[48] = "乔";

		LAST_NAME[49] = "秦";

		LAST_NAME[50] = "邱";

		LAST_NAME[51] = "裘";

		LAST_NAME[52] = "仇";

		LAST_NAME[53] = "沙";

		LAST_NAME[54] = "商";

		LAST_NAME[55] = "尚";

		LAST_NAME[56] = "邵";

		LAST_NAME[57] = "沈";

		LAST_NAME[58] = "师";

		LAST_NAME[59] = "施";

		LAST_NAME[60] = "宋";

		LAST_NAME[61] = "孙";

		LAST_NAME[62] = "童";

		LAST_NAME[63] = "万";

		LAST_NAME[64] = "王";

		LAST_NAME[65] = "魏";

		LAST_NAME[66] = "卫";

		LAST_NAME[67] = "吴";

		LAST_NAME[68] = "武";

		LAST_NAME[69] = "萧";

		LAST_NAME[70] = "肖";

		LAST_NAME[71] = "项";

		LAST_NAME[72] = "许";

		LAST_NAME[73] = "徐";

		LAST_NAME[74] = "薛";

		LAST_NAME[75] = "杨";

		LAST_NAME[76] = "羊";

		LAST_NAME[77] = "阳";

		LAST_NAME[78] = "易";

		LAST_NAME[79] = "尹";

		LAST_NAME[80] = "俞";

		LAST_NAME[81] = "赵";

		LAST_NAME[82] = "钟";

		LAST_NAME[83] = "周";

		LAST_NAME[84] = "郑";

		LAST_NAME[85] = "朱";

		// LAST_NAME[86] = "东方";

		// LAST_NAME[87] = "独孤";

		// LAST_NAME[88] = "慕容";

		// LAST_NAME[89] = "欧阳";

		// LAST_NAME[90] = "司马";

		// LAST_NAME[91] = "西门";

		// LAST_NAME[92] = "尉迟";

		// LAST_NAME[93] = "长孙";

		// LAST_NAME[94] = "诸葛";

		FIRST_NAME[0] = "皑艾哀";

		FIRST_NAME[1] = "安黯谙";

		FIRST_NAME[2] = "奥傲敖骜翱";

		FIRST_NAME[3] = "昂盎";

		FIRST_NAME[4] = "罢霸";

		FIRST_NAME[5] = "白佰";

		FIRST_NAME[6] = "斑般";

		FIRST_NAME[7] = "邦";

		FIRST_NAME[8] = "北倍贝备";

		FIRST_NAME[9] = "表标彪飚飙";

		FIRST_NAME[10] = "边卞弁忭";

		FIRST_NAME[11] = "步不";

		FIRST_NAME[12] = "曹草操漕";

		FIRST_NAME[13] = "苍仓";

		FIRST_NAME[14] = "常长昌敞玚";

		FIRST_NAME[15] = "迟持池赤尺驰炽";

		FIRST_NAME[16] = "此次词茨辞慈";

		FIRST_NAME[17] = "独都";

		FIRST_NAME[18] = "东侗";

		FIRST_NAME[19] = "都";

		FIRST_NAME[20] = "发乏珐";

		FIRST_NAME[21] = "范凡反泛帆蕃";

		FIRST_NAME[22] = "方访邡昉";

		FIRST_NAME[23] = "风凤封丰奉枫峰锋";

		FIRST_NAME[24] = "夫符弗芙";

		FIRST_NAME[25] = "高皋郜镐";

		FIRST_NAME[26] = "洪红宏鸿虹泓弘";

		FIRST_NAME[27] = "虎忽湖护乎祜浒怙";

		FIRST_NAME[28] = "化花华骅桦";

		FIRST_NAME[29] = "号浩皓蒿浩昊灏淏";

		FIRST_NAME[30] = "积极济技击疾及基集记纪季继吉计冀祭际籍绩忌寂霁稷玑芨蓟戢佶奇诘笈畿犄";

		FIRST_NAME[31] = "渐剑见建间柬坚俭";

		FIRST_NAME[32] = "刊戡";

		FIRST_NAME[33] = "可克科刻珂恪溘牁";

		FIRST_NAME[34] = "朗浪廊琅阆莨";

		FIRST_NAME[35] = "历离里理利立力丽礼黎栗荔沥栎璃";

		FIRST_NAME[36] = "临霖林琳";

		FIRST_NAME[37] = "马";

		FIRST_NAME[38] = "贸冒貌冒懋矛卯瑁";

		FIRST_NAME[39] = "淼渺邈";

		FIRST_NAME[40] = "楠南";

		FIRST_NAME[41] = "片翩";

		FIRST_NAME[42] = "潜谦倩茜乾虔千";

		FIRST_NAME[43] = "强羌锖玱";

		FIRST_NAME[44] = "亲琴钦沁芩矜";

		FIRST_NAME[45] = "清庆卿晴";

		FIRST_NAME[46] = "冉然染燃";

		FIRST_NAME[47] = "仁刃壬仞";

		FIRST_NAME[48] = "沙煞";

		FIRST_NAME[49] = "上裳商";

		FIRST_NAME[50] = "深审神申慎参莘";

		FIRST_NAME[51] = "师史石时十世士诗始示适炻";

		FIRST_NAME[52] = "水";

		FIRST_NAME[53] = "思斯丝司祀嗣巳";

		FIRST_NAME[54] = "松颂诵";

		FIRST_NAME[55] = "堂唐棠瑭";

		FIRST_NAME[56] = "统通同童彤仝";

		FIRST_NAME[57] = "天田忝";

		FIRST_NAME[58] = "万宛晚";

		FIRST_NAME[59] = "卫微伟维威韦纬炜惟玮为";

		FIRST_NAME[60] = "吴物务武午五巫邬兀毋戊";

		FIRST_NAME[61] = "西席锡洗夕兮熹惜";

		FIRST_NAME[62] = "潇萧笑晓肖霄骁校";

		FIRST_NAME[63] = "熊雄";

		FIRST_NAME[64] = "羊洋阳漾央秧炀飏鸯";

		FIRST_NAME[65] = "易意依亦伊夷倚毅义宜仪艺译翼逸忆怡熠沂颐奕弈懿翊轶屹猗翌";

		FIRST_NAME[66] = "隐因引银音寅吟胤訚烟荫";

		FIRST_NAME[67] = "映英影颖瑛应莹郢鹰";

		FIRST_NAME[68] = "幽悠右忧猷酉";

		FIRST_NAME[69] = "渔郁寓于余玉雨语预羽舆育宇禹域誉瑜屿御渝毓虞禺豫裕钰煜聿";

		FIRST_NAME[70] = "制至值知质致智志直治执止置芝旨峙芷挚郅炙雉帜";

		FIRST_NAME[71] = "中忠钟衷";

		FIRST_NAME[72] = "周州舟胄繇昼";

		FIRST_NAME[73] = "竹主驻足朱祝诸珠著竺";

		FIRST_NAME[74] = "卓灼灼拙琢濯斫擢焯酌";

		FIRST_NAME[75] = "子资兹紫姿孜梓秭";

		FIRST_NAME[76] = "宗枞";

		FIRST_NAME[77] = "足族祖卒";

		FIRST_NAME[78] = "作左佐笮凿";
	}

	public String generate() {

		return generate(MIN_LENGTH, MAX_LENGTH);

	}

	public String generate(int minLength, int maxLength) {
		int min = minLength;
		int max = maxLength;
		if (min == 0 || max == 0) {
			min = MIN_LENGTH;
			max = MAX_LENGTH;
		}
		
		int length = randomInt(min - 1, max);
		String lastNameStr = LAST_NAME[RANDOM.nextInt(LAST_NAME.length)];
		StringBuilder buff = new StringBuilder(max);
		buff.append(lastNameStr);
		int k = 0;
		while (k < length) {
			int j = RANDOM.nextInt(FIRST_NAME.length);
			buff.append(FIRST_NAME[j].substring(FIRST_NAME[j].length() - 1, FIRST_NAME[j].length()));
			k++;
		}
		return buff.toString();
	}
}
