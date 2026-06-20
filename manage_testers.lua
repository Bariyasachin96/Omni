require 'import'

-- ===== CONFIG =====
local DB_URL    = "https://easy-voice-e53f0-default-rtdb.firebaseio.com"
local DB_SECRET = "YAHAN_APNA_DATABASE_SECRET_PASTE_KARO"
-- ==================

local function rtdbKey(email)
    return (email:gsub("%.", ","))
end

local function firebaseRequest(method, email)
    local key = rtdbKey(email:trim())
    local url = DB_URL .. "/testers/" .. key .. ".json?auth=" .. DB_SECRET
    local body = method == "PUT" and '{"active":true}' or nil

    local ok, result = pcall(function()
        local jURL  = luajava.bindClass("java.net.URL")
        local conn  = jURL(url):openConnection()
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
        local code = conn:getResponseCode()
        return code
    end)
    return ok and result or -1
end

local function runInThread(fn)
    local t = luajava.newInstance("java.lang.Thread",
        luajava.createProxy("java.lang.Runnable", { run = fn }))
    t:start()
end

-- UI
activity:setTitle("Easy Voice — Tester Manager")

local root = LinearLayout(activity)
root:setOrientation(1) -- VERTICAL
root:setPadding(40,40,40,40)

local title = TextView(activity)
title:setText("Tester Manager")
title:setTextSize(22)
root:addView(title)

local space1 = Space(activity)
space1:setMinimumHeight(20)
root:addView(space1)

local emailField = EditText(activity)
emailField:setHint("Email: name@gmail.com")
emailField:setInputType(0x21) -- text + email
root:addView(emailField)

local space2 = Space(activity)
space2:setMinimumHeight(16)
root:addView(space2)

local addBtn = Button(activity)
addBtn:setText("Add Tester")
root:addView(addBtn)

local removeBtn = Button(activity)
removeBtn:setText("Remove Tester")
root:addView(removeBtn)

local space3 = Space(activity)
space3:setMinimumHeight(20)
root:addView(space3)

local statusView = TextView(activity)
statusView:setText("")
statusView:setTextSize(16)
root:addView(statusView)

local function setStatus(msg)
    activity:runOnUiThread(function()
        statusView:setText(msg)
    end)
end

addBtn:onClick(function()
    local email = emailField:getText():toString()
    if email == "" then setStatus("Email daalo!") return end
    setStatus("Adding...")
    runInThread(function()
        local code = firebaseRequest("PUT", email)
        if code == 200 then
            setStatus("ADDED: " .. email)
        else
            setStatus("Error: " .. tostring(code) .. " — secret check karo")
        end
    end)
end)

removeBtn:onClick(function()
    local email = emailField:getText():toString()
    if email == "" then setStatus("Email daalo!") return end
    setStatus("Removing...")
    runInThread(function()
        local code = firebaseRequest("DELETE", email)
        if code == 200 then
            setStatus("REMOVED: " .. email)
        else
            setStatus("Error: " .. tostring(code) .. " — secret check karo")
        end
    end)
end)

activity:setContentView(root)
