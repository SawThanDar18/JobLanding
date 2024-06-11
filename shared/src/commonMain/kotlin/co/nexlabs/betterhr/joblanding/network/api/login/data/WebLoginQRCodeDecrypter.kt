package co.nexlabs.betterhr.joblanding.network.api.login.data

import io.jsonwebtoken.Jwts
import java.util.Calendar

class WebLoginQRCodeDecrypter {

    companion object {
        private const val MAX_TIME = 5 * 60
    }


    operator fun invoke(qrString: String): WebLoginQRData? {
        //BetterJobLanding joblandingdev 1717664582642
        val splitter = qrString.split(" ")
        if (splitter.size < 2 || splitter[0] != "betterJobLanding") {
            throw InvalidQRCodeException()
        }

        val decryptedQRString = Jwts.parser()
            //.setSigningKey(TextCodec.BASE64.encode("BetterJobLanding"))
            .parseClaimsJws(splitter[1])
            .body.subject
        val values = decryptedQRString.split(" ")
        val prefix = values[0]
        val domain = values[1]
        val timestamp = values[2].toLong()

        if (prefix != "BetterJobLanding") throw InvalidQRCodeException()

        if (domain != "joblandingdev") throw IncorrectDomainException()

        val nowTimestamp = Calendar.getInstance().timeInMillis / 1000
        if ((nowTimestamp - timestamp) > MAX_TIME) throw QRDataExpiredException()

        return WebLoginQRData(decryptedQRString)
    }

}