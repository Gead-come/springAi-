package cn.itcast;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeatherToolsTest {

    private final WeatherTools weatherTools = new WeatherTools();

    @Test
    void getWeather() {
        // 给定一个城市ID（任意字符串，因为方法里不实际使用）
        String cityId = "101010100";

        // 调用被测方法
        WeatherDTO result = weatherTools.getWeather(cityId);

        // 验证返回对象不为 null
        assertNotNull(result);

        // 验证各个字段是否符合模拟返回的预期值
        assertEquals(cityId, result.getCityId());
        assertEquals("北京", result.getCity());
        assertEquals("25", result.getTemperature());
        assertEquals("20", result.getLowTemperature());
        assertEquals("30", result.getHighTemperature());
        assertEquals("2023-10-01", result.getDate());
        assertEquals("良", result.getQuality());
        assertEquals(15.5, result.getPm25(), 0.01); // double 类型需指定 delta
    }
}