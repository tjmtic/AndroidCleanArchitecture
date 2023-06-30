package com.tiphubapps.ax.rain.util

//import com.github.nkzawa.socketio.client.Socket
//import io.socket.client.IO
//import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    //lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
// "http://10.0.2.2:3000" is the network your Android emulator must use to join the localhost network on your computer
// "http://localhost:3000/" will not work
// If you want to use your physical phone you could use your ip address plus :3000
// This will allow your Android Emulator and physical device at your home to connect to the server
            //val authToken = "eyJraWQiOiJ1WnFRMFlBQk45N0VUY0JWZ3NBR2N0NFdab0cxVzhuQThtUWhXSFV3R0NrPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJjMmUyY2U5Yi0yYTRmLTQ3ZmYtYWFkYS1kZWIyY2M2MDkxNDgiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX2xXb0lZRWJwZyIsImNvZ25pdG86dXNlcm5hbWUiOiJjMmUyY2U5Yi0yYTRmLTQ3ZmYtYWFkYS1kZWIyY2M2MDkxNDgiLCJhdWQiOiIzaTBtZDZrM2s0OTkyNWY2ZWsxYmRyNTFlYiIsImV2ZW50X2lkIjoiOTcwNDhmZTYtOWUxZC00ZGU2LThiODQtMTBhNThlZGJkODZlIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE2NTMzNjQ0MzAsIm5hbWUiOiJKYXkiLCJleHAiOjE2NTMzNjgwMzAsImlhdCI6MTY1MzM2NDQzMCwiZW1haWwiOiJqYXlAMDI2MHRlY2guY29tIn0.ifBm_T5Ypb8KYnkzktBrbrLumm271GDqwrf5bQrhot_qEGbt1q8TJnZ0w5ALMxqnmhR2uVA4sWogb0_85m3YBEmxXZpd5tp0dg5q9W7YDGc0jce6va2fqPtXpUbis0LF-Dfv9XEQ8kFXQqIh0JQueAe3n6VPhKMuOixx1mpjtsN1VHO3J-2P7KukPwwuQqR-6GElKEmKlLLp95_s3jJV5nuc3lkZPGq2mzA172Ud3NAGs8_cLcU1NLq_2YuxwHpHm4FIVJhwI13T3udOEowc_vCEMAyh6ad13ibc--YofCiVVwAYlXcInpF_a4oTDUgnUtIrp0FLVyuJse94BHsLng"
            val authToken =
                "eyJraWQiOiJ1WnFRMFlBQk45N0VUY0JWZ3NBR2N0NFdab0cxVzhuQThtUWhXSFV3R0NrPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJjMmUyY2U5Yi0yYTRmLTQ3ZmYtYWFkYS1kZWIyY2M2MDkxNDgiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX2xXb0lZRWJwZyIsImNvZ25pdG86dXNlcm5hbWUiOiJjMmUyY2U5Yi0yYTRmLTQ3ZmYtYWFkYS1kZWIyY2M2MDkxNDgiLCJhdWQiOiIzaTBtZDZrM2s0OTkyNWY2ZWsxYmRyNTFlYiIsImV2ZW50X2lkIjoiNDFlZWY3NWMtOTQ0Zi00ZDgxLWE4MjUtYjgwNTlhZDhiZDlhIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE2NjE2NTIzODMsIm5hbWUiOiJKYXkiLCJleHAiOjE2NjE2NTU5ODMsImlhdCI6MTY2MTY1MjM4MywiZW1haWwiOiJqYXlAMDI2MHRlY2guY29tIn0.IbPgoHI43KiQ1gi-quPtP-4Y4AC-XesQ2mqaGKdtlmMq5pSnuO3sRNeCUjNMZzMRPsJnasx5Bh9lQwF6NGMjUO4c10vu6SnM4SLz2OGUEWccYU01QclgIyYaEbLNii6j8NtIfLt0Tc2GNUkFWMdfj9Y7JC0oFjswlABZO8FtfHDyUMhENOEpJzqTMFqWIxOVHBo4_A5lxoIX9b18jDBOnFC7osQrCXcx4etemmnHetxQvftRyBB0FZBmXwrmCJaqOjW2CrHrlXjkDW-oZS9LPVcIFLVvSPywYDpNKkKV1qAfQWYf3r4k5iHtm0TWD_VBeO1IMXbG0TADx-NCULpCQQ"
            //val authToken = "eyJraWQiOiJ1WnFRMFlBQk45N0VUY0JWZ3NBR2N0NFdab0cxVzhuQThtUWhXSFV3R0NrPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIyNWFhZTc5NS0wYjNhLTRlNmEtYWU2ZC0wZDk3NmMxMGM0NDAiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX2xXb0lZRWJwZyIsImNvZ25pdG86dXNlcm5hbWUiOiIyNWFhZTc5NS0wYjNhLTRlNmEtYWU2ZC0wZDk3NmMxMGM0NDAiLCJhdWQiOiIzaTBtZDZrM2s0OTkyNWY2ZWsxYmRyNTFlYiIsImV2ZW50X2lkIjoiYmUyNzI1MmYtMWZiNC00ODlmLTk0ZmMtN2MxNDYxNDU5NjZiIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE2NjE2NTMzMDEsIm5hbWUiOiJBVGVzdGVyc29uIiwiZXhwIjoxNjYxNjU2OTAxLCJpYXQiOjE2NjE2NTMzMDEsImVtYWlsIjoidGVzdEB0ZXN0aWVzLmNvbSJ9.hTDuLDbG9WZnvwbiYSqa2yktrVqTgulK7u9sutOmiIMWheHHida7IrFA0QkD7XZpZ8h24VoCNRJG7GbTHAosUIUonHWzV6TsDEEg3wjwqFWCWEcIlCYzNkT98hJmOJGCobMYxU_eCmKbLHDcGznWPRy0Ch6AzvOkMgX_vFoB9oDGlKU6p_Eo_vXJVGaLp9fF1VXJWevZlOdsHarCjtmcvt41rzQLnE9KAx1OlDAuc1Pgh0_2Gk1-aa_69-NvYvGWpSvbV_7bKs6sP0PvZet5Dp9ItL3KkZw8ovBRTBo-ATKsDqqS-qAfmfeNxTs0lJ3Z57L-TLiQyQoOtIbNkEEJFw"
            val tokenList: List<String> = emptyList<String>().plus(authToken)
            var headers = mapOf("token" to tokenList)//List<String, List<String>>(1)

            // headers.set("token", tokenList)
            /*val opts = IO.Options()
            opts.reconnection = true
            opts.reconnectionAttempts = -1
            opts.reconnectionDelay = 3
            opts.reconnectionDelayMax = 3
            opts.forceNew = true
           // opts.extraHeaders = headers
            opts.query = "token=" + authToken;
            mSocket = IO.socket(BuildConfig.SOCKET_URL, opts)
        */} catch (e: URISyntaxException) {
                println(e)
                println("THERE WAS AN ERROR WITH THE SOCKET IO CONFIG")
        }

    }

    //@Synchronized
    /*fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun authenticate() {
        mSocket.emit("auth")
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
        authenticate()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }*/
}