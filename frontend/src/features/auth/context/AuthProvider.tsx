import type { PropsWithChildren } from "react";

import {
    useMemo,
    useState,
} from "react";

import {
    AuthContext,
} from "./AuthContext";

import {
    clearAuth,
    getRole,
    getToken,
    getUsername,
    saveAuth,
} from "../utils/tokenStorage";

export default function AuthProvider({
    children,
}: PropsWithChildren) {
    const [token, setToken] = useState(getToken());

    const [username, setUsername] = useState(getUsername());

    const [role, setRole] = useState(getRole());

    function login(
        token: string,
        username: string,
        role: string
    ) {
        saveAuth(token, username, role);

        setToken(token);
        setUsername(username);
        setRole(role);
    }

    function logout() {
        clearAuth();

        setToken(null);
        setUsername(null);
        setRole(null);
    }

    const value = useMemo(() => ({
        isAuthenticated: token !== null,
        username,
        role,
        login,
        logout,
    }), [token, username, role]);

    return (
        <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
    )
}