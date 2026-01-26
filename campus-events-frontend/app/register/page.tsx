"use client";

import { useState } from "react";
import { register } from "../../services/api";
import { useRouter } from "next/navigation";

export default function RegisterPage() {
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    role: "STUDENT",
  });

  const router = useRouter();

  async function handleSubmit(e: any) {
    e.preventDefault();

    try {
      await register(form);
      router.push("/login");
    } catch (err: any) {
      alert(err.message || "Registration failed");
    }
  }

  return (
    <div className="h-screen flex justify-center items-center">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded shadow w-80"
      >
        <h2 className="text-xl mb-4">Register</h2>

        {["name", "email", "password"].map((field) => (
          <input
            key={field}
            placeholder={field}
            type={field === "password" ? "password" : "text"}
            className="border w-full mb-2 p-2"
            value={(form as any)[field]}
            onChange={(e) =>
              setForm({ ...form, [field]: e.target.value })
            }
          />
        ))}

        <select
          className="border w-full mb-2 p-2"
          value={form.role}
          onChange={(e) => setForm({ ...form, role: e.target.value })}
        >
          <option value="STUDENT">Student</option>
          <option value="ORGANIZER">Organizer</option>
          <option value="ADMIN">Admin</option>
        </select>

        <button className="bg-green-500 text-white w-full py-2 rounded">
          Register
        </button>
      </form>
    </div>
  );
}
