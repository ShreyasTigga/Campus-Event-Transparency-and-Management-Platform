"use client";

import { useState } from "react";
import { login as apiLogin } from "../services/api";
import { useAuth } from "../context/AuthContext";
import { useRouter } from "next/navigation";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useAuth();
  const router = useRouter();

  async function handleSubmit(e: any) {
    e.preventDefault();

    const res = await apiLogin(email, password);

    console.log("JWT Token:", res.token); // 🔍 DEBUG LINE

    login(res.token); // ✅ CORRECT
    router.push("/dashboard");
  }

  return (
    <div className="h-screen flex justify-center items-center">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded shadow w-80"
      >
        <h2 className="text-xl mb-4">Login</h2>

        <input
          placeholder="Email"
          className="border w-full mb-2 p-2"
          value={email}
          onChange={e => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          className="border w-full mb-2 p-2"
          value={password}
          onChange={e => setPassword(e.target.value)}
        />

        <button className="bg-blue-500 text-white w-full py-2 rounded">
          Login
        </button>
      </form>
    </div>
  );
}
