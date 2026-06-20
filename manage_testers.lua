require 'import'

local SA_EMAIL  = "firebase-adminsdk-fbsvc@easy-voice-e53f0.iam.gserviceaccount.com"
local SA_KEY    = [[-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDIupudAR/gXidY
8aiqsfrUEgpDHr4qj/7BF6dBm/JV4QqkOCE9Mv4KJMsgK3fMRqQWk8kCkFuCybDn
wXHkIWXTIVTLO1/ZSPdW0bVeqvDfj2USGjMdMlxYlVu8E5tp7hnu5VJB06JEcP7/
qOgNEQtIpAkVbRAYaoHIrT1xMHf4IfNx0/6wir4hblmgD+Z7mYE/Hr/GG7kJTU8V
1yI/hWNyLQl9roY2rTgVFIohpxeRdw3xCOYUHQpbC1vDDJzinwu8iaNgLOBaHTeE
sb0rypeExNnocrX74SdAXz+uwR4HqplV7VSMBb8p1pSHsPs6db4639fOoHsR/sZZ
dxShVk1xAgMBAAECggEAJEml+a17HYTv8JKpdwhba8zCGa28W7Xx7GbyB/ihB/j5
w0PZcHagOeW6afupud54ahN39pRx0rRTHKaaSA9omNFRAreeZYndGw8ozm8xQf0q
nKnrXSceTi5cKeVShoUyn39KDlfr4RsbYk0e/EO9SjnLYZn4m61ffo+hCK4mNg9c
vwQCtm/4Ujqdn76r5U3L9xOCS/Y++EOfJVLALRZJ9g9NZo0ZIIEh1YJWKHqPusIS
blh+f8Y2Gs6v/y11aQXN2fEU+fwPnJZOPvSD35qHF0+hgRrdyKpePF4ouuYcvjsT
ogEMminjQuTU3BSpTJUDTGNE1edUBxxJdYSvPFDEZQKBgQD86ZNwkEavuVa3QDBd
WnZP5WmzhoICtQdslNXTu5j9OrH+A0HOAigODjpHCRdfZs2G7dCdE2tPekWn/BW5
MMCXrs1PBsBrzFXbX5t7Tz0bSJ6jVZcRFpvLoLrUN6zHNyI+EN2HH10itk55VGTF
+K7pT2/o1p+4uNABal82EIgVLQKBgQDLLfGNpXo3GZlfyIRokqoWfVrOlNHE1qEB
Zf4aeMkY1pTkxYbjRELSZ8exN5A6Y1UFmKtrJyapVnY+t1k75Z0+o9rlIPfIm+71
kYuh/vyAFESTX2hw29M+2+Q/BG7+4YUfrhS4o4wYpoBvfjieOIcAGrfl0zfVLNqC
aKM6C9rL1QKBgEeV/Yug6sEWXrsjygzCbDaVIuQTsHz/rIhJYHS75sPWd0cEuP2M
krXXxRsSMbFpMcd9TM0JEBjA7TJtIeD3Maw9aBN0P8bDYZHkb+fpN8yfM3fb5BSM
J7DtX7RtxdPlgSW8NFfowr3uiCvDfXqjty6Gs5BeH3tzpDyiVSzCZSJJAoGAFb+C
Np462cEEuMr22VaxnByo5qnpVj9/Fm47Z8v8AoudfmtiR9tbp7fyGRcQeh99z06L
MroLGl8l4wqvI4ktbrINUS9ZNoDycBi3MIzhrDf/KyMwQ7xT/Ut+P/ySgPfUzxJM
J4NiEJVp9AU3vD/iu3EgtBoKxOe7L/BRYHo6Xk0CgYEA1d2Pe/xn5nWULU6Jv6So
xVQ4Zj1PWVCmm2q0ocERZErDRSPLqau3ak40E0NANTKV2qbhddIGYeSpiayLGfz5
dilK67z0ilRJK45+8LVF+d6WYxJm3fPvDlB9S88cdZoX5yH1Ru0Q0/6yr9tZjDHD
v4pKtRAuKXtBMLo0Bz/zEhk=
-----END PRIVATE KEY-----]]
local DB_URL = "https://easy-voice-e53f0-default-rtdb.firebaseio.com"

-- Base64URL encode bytes
local function b64url(bytes)
    local B = luajava.bindClass("android.util.Base64")
    return tostring(B.encodeToString(bytes, 11)) -- NO_PADDING|NO_WRAP|URL_SAFE
end

local function b64url_str(s)
    return b64url(luajava.newInstance("java.lang.String", s):getBytes("UTF-8"))
end

-- Load RSA private key from PEM
local function loadKey(pem)
    local keyStr = pem:gsub("%-%-%-%-%-[^\n]+%-%-%-%-%-", ""):gsub("%s","")
    local B = luajava.bindClass("android.util.Base64")
    local keyBytes = B.decode(keyStr, 0)
    local KF   = luajava.bindClass("java.security.KeyFactory")
    local Spec = luajava.bindClass("java.security.spec.PKCS8EncodedKeySpec")
    return KF.getInstance("RSA"):generatePrivate(Spec(keyBytes))
end

-- Sign string with RS256
local function rs256(data, key)
    local Sig = luajava.bindClass("java.security.Signature")
    local sig = Sig.getInstance("SHA256withRSA")
    sig:initSign(key)
    sig:update(luajava.newInstance("java.lang.String", data):getBytes("UTF-8"))
    return sig:sign()
end

-- Get OAuth2 access token from service account
local function getToken()
    local t   = math.floor(os.time())
    local hdr = b64url_str('{"alg":"RS256","typ":"JWT"}')
    local pay = b64url_str(string.format(
        '{"iss":"%s","scope":"https://www.googleapis.com/auth/firebase","aud":"https://oauth2.googleapis.com/token","iat":%d,"exp":%d}',
        SA_EMAIL, t, t+3600))
    local unsigned = hdr .. "." .. pay
    local jwt = unsigned .. "." .. b64url(rs256(unsigned, loadKey(SA_KEY)))

    local body = "grant_type=urn%3Aietf%3Aparams%3Aoauth%3Agrant-type%3Ajwt-bearer&assertion=" .. jwt
    local URL  = luajava.bindClass("java.net.URL")
    local conn = URL("https://oauth2.googleapis.com/token"):openConnection()
    conn:setRequestMethod("POST")
    conn:setDoOutput(true)
    conn:setRequestProperty("Content-Type","application/x-www-form-urlencoded")
    local os = conn:getOutputStream()
    os:write(luajava.newInstance("java.lang.String", body):getBytes("UTF-8"))
    os:close()

    local code   = conn:getResponseCode()
    local stream = code == 200 and conn:getInputStream() or conn:getErrorStream()
    local reader = luajava.newInstance("java.io.BufferedReader",
                       luajava.newInstance("java.io.InputStreamReader", stream))
    local resp = ""
    local line = reader:readLine()
    while line do resp = resp .. tostring(line); line = reader:readLine() end

    return resp:match('"access_token"%s*:%s*"([^"]+)"')
end

-- Firebase REST call
local function fbRequest(method, email, token)
    local key = email:match("^%s*(.-)%s*$"):gsub("%.", ",")
    local url = DB_URL .. "/testers/" .. key .. ".json?access_token=" .. token
    local body = method == "PUT" and '{"active":true}' or nil

    local URL  = luajava.bindClass("java.net.URL")
    local conn = URL(url):openConnection()
    conn:setRequestMethod(method)
    conn:setConnectTimeout(10000)
    conn:setReadTimeout(10000)
    if body then
        conn:setDoOutput(true)
        conn:setRequestProperty("Content-Type","application/json")
        local os = conn:getOutputStream()
        os:write(luajava.newInstance("java.lang.String", body):getBytes("UTF-8"))
        os:close()
    end
    return conn:getResponseCode()
end

local function runBg(fn)
    local t = luajava.newInstance("java.lang.Thread",
        luajava.createProxy("java.lang.Runnable", {run = fn}))
    t:start()
end

-- UI
activity:setTitle("Tester Manager")
local root = LinearLayout(activity)
root:setOrientation(1)
root:setPadding(40,50,40,40)

local lbl = TextView(activity)
lbl:setText("Easy Voice — Tester Manager")
lbl:setTextSize(20)
root:addView(lbl)

local sp = Space(activity); sp:setMinimumHeight(24); root:addView(sp)

local emailField = EditText(activity)
emailField:setHint("Email: name@gmail.com")
root:addView(emailField)

local sp2 = Space(activity); sp2:setMinimumHeight(16); root:addView(sp2)

local addBtn = Button(activity)
addBtn:setText("Add Tester")
root:addView(addBtn)

local removeBtn = Button(activity)
removeBtn:setText("Remove Tester")
root:addView(removeBtn)

local sp3 = Space(activity); sp3:setMinimumHeight(20); root:addView(sp3)

local status = TextView(activity)
status:setText("")
status:setTextSize(15)
root:addView(status)

local function setStatus(msg)
    activity:runOnUiThread(function() status:setText(msg) end)
end

local function doAction(method)
    local email = emailField:getText():toString()
    if email:match("^%s*$") then setStatus("Email daalo!"); return end
    setStatus("Connecting...")
    runBg(function()
        local ok, token = pcall(getToken)
        if not ok or not token then
            setStatus("Token error — internet check karo"); return
        end
        local ok2, code = pcall(fbRequest, method, email, token)
        if ok2 and code == 200 then
            local action = method == "PUT" and "ADDED" or "REMOVED"
            setStatus(action .. ": " .. email:match("^%s*(.-)%s*$"))
        else
            setStatus("Error: " .. tostring(ok2 and code or code))
        end
    end)
end

addBtn:onClick(function() doAction("PUT") end)
removeBtn:onClick(function() doAction("DELETE") end)

activity:setContentView(root)
