const TOKEN_KEY = "jwt";
const USERNAME_KEY = "username";
const ROLE_KEY = "role";

export function saveAuth(
    token: string,
    username: string,
    role: string
): void {
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USERNAME_KEY, username);
    localStorage.setItem(ROLE_KEY, role);
}

export function getUsername(): string | null {
    return localStorage.getItem(USERNAME_KEY);
}

export function getRole(): string | null {
    return localStorage.getItem(ROLE_KEY);
}

export function clearAuth(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USERNAME_KEY);
    localStorage.removeItem(ROLE_KEY);
}

export function saveToken(token: string): void {
    localStorage.setItem(TOKEN_KEY, token);
}

export function getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
}

export function removeToken(): void {
    localStorage.removeItem(TOKEN_KEY);
}