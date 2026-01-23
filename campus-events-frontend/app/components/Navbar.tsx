"use client";

import { useAuth } from "../context/AuthContext";
import { useRouter } from "next/navigation";

export default function Navbar() {
  const { role, logout } = useAuth();
  const router = useRouter();

  return (
    <div className="flex justify-between items-center p-4 bg-blue-600 text-white">
      <h1 className="text-xl font-bold">Campus Events</h1>

      <div className="flex gap-4">
        <span>{role}</span>
        <button
          onClick={() => {
            logout();
            router.push("/login");
          }}
          className="bg-red-500 px-3 py-1 rounded"
        >
          Logout
        </button>
      </div>
    </div>
  );
}
