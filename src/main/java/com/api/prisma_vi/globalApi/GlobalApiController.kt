package com.api.prisma_vi.globalApi

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class GlobalApiController {

    @PostMapping("/mock/search-color")
    fun mockSearchColor(@RequestBody color: String): ResponseEntity<String> =
        ResponseEntity.ok(
            "{\n" +
                "\"Color\": {\n" +
                "\"name\": \"Forest Night\",\n" +
                "\"hexCode\": \"$color\",\n" +
                "\"rgbCode\": \"3, 10, 0\",\n" +
                "\"rybPercentages\": {\n" +
                "\"r\": \"0%\",\n" +
                "\"y\": \"0%\",\n" +
                "\"b\": \"100%\"\n" +
                "},\n" +
                "\"colorTemperature\": \"cold\",\n" +
                "\"colorDescription\": \"A deep, rich shade of dark green, evoking feelings of mystery, tranquility, and the night.  It could also be associated with forests and lush vegetation.\",\n" +
                "\"twoHexOfColorsThatMatch\": [\n" +
                "\"#001e00\",\n" +
                "\"#020800\"\n" +
                "],\n" +
                "\"colorTerminology\": \"terrestrial\"\n" +
                "}\n" +
                "}")


    @GetMapping("/is-running")
    fun isApiRunning(): ResponseEntity<String> {
        val gifUrl: String = "https://i0.wp.com/motiongraphicsphoebe.wordpress.com/wp-content/uploads/2018/10/loading-animations-preloader-gifs-ui-ux-effects-18.gif?w=1000&h=&ssl=1"
        val html: String =
            "<body style=\"padding: 0; margin: 0;\">\n" +
                    "    <div style=\"width: 100vw; height: 100vh; display: flex; align-items: center; justify-content: center; flex-direction: column;\">\n" +
                    "        <H1>Service is running</H1>\n" +
                    "        <img src=\"${gifUrl}\" alt=\"Running\">\n" +
                    "    </div>\n" +
                    "</body>"
        return ResponseEntity(html, HttpStatus.OK)
    }
}