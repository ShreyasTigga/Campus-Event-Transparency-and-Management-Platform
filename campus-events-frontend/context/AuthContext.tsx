"use client";

import { createContext, useContext, useEffect, useState } from "react";

type AuthContextType = {
  token: string | null;
  role: string | null;
  login: (jwt: string) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextType | null>(null);

function parseJwt(token: string) {
  try {
    const base64Payload = token.split(".")[1];
    return JSON.parse(atob(base64Payload));
  } catch {
    return null;
  }
}

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(null);
  const [role, setRole] = useState<string | null>(null);

  useEffect(() => {
    const saved = localStorage.getItem("token");
    if (saved) {
      const payload = parseJwt(saved);
      setToken(saved);
      setRole(payload?.role || null);
    }
  }, []);

  function login(jwt: string) {
    const payload = parseJwt(jwt);
    localStorage.setItem("token", jwt);
    setToken(jwt);
    setRole(payload?.role || null);
  }

  function logout() {
    localStorage.removeItem("token");
    setToken(null);
    setRole(null);
  }

  return (
    <AuthContext.Provider value={{ token, role, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used inside AuthProvider");
  return ctx;
}
