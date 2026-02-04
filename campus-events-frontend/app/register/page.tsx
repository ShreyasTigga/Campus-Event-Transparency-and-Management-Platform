"use client";

import { useState } from "react";
import { register } from "../../services/api";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { motion } from "framer-motion";

export default function RegisterPage() {
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    role: "STUDENT",
  });

  const [loading, setLoading] = useState(false);
  const router = useRouter();

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    if (form.password !== form.confirmPassword) {
      alert("Passwords do not match");
      return;
    }

    setLoading(true);

    try {
      const { confirmPassword, ...payload } = form; // remove confirmPassword before sending
      await register(payload);
      router.push("/login");
    } catch (err: any) {
      alert(err.message || "Registration failed");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="relative min-h-screen overflow-hidden bg-[#020617]">
      {/* Animated Background */}
      <div className="absolute inset-0 bg-gradient-to-br from-blue-900/40 via-purple-900/40 to-cyan-900/40 animate-gradient" />

      {/* Floating Orbs */}
      <div className="absolute -top-40 -left-40 w-96 h-96 bg-blue-500/30 rounded-full blur-3xl animate-float" />
      <div className="absolute top-1/3 -right-40 w-96 h-96 bg-purple-500/30 rounded-full blur-3xl animate-float-slow" />
      <div className="absolute bottom-0 left-1/3 w-96 h-96 bg-cyan-500/30 rounded-full blur-3xl animate-float-reverse" />

      {/* Register Card */}
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.8 }}
        className="relative z-10 flex min-h-screen items-center justify-center px-6"
      >
        <form
          onSubmit={handleSubmit}
          className="w-full max-w-md rounded-2xl border border-white/10 bg-white/5 backdrop-blur-xl p-8 shadow-2xl"
        >
          <h2 className="text-3xl font-bold text-white text-center mb-2">
            Create Account
          </h2>
          <p className="text-white/70 text-center mb-8">
            Join the campus event platform
          </p>

          {/* Name */}
          <div className="mb-4">
            <label className="block text-white/80 mb-1 text-sm">
              Full Name
            </label>
            <input
              type="text"
              placeholder="John Doe"
              className="w-full rounded-lg bg-white/10 border border-white/20 px-4 py-3 text-white placeholder-white/40 outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-400/40 transition"
              value={form.name}
              onChange={(e) =>
                setForm({ ...form, name: e.target.value })
              }
              required
            />
          </div>

          {/* Email */}
          <div className="mb-4">
            <label className="block text-white/80 mb-1 text-sm">
              Email Address
            </label>
            <input
              type="email"
              placeholder="student@campus.edu"
              className="w-full rounded-lg bg-white/10 border border-white/20 px-4 py-3 text-white placeholder-white/40 outline-none focus:border-purple-400 focus:ring-2 focus:ring-purple-400/40 transition"
              value={form.email}
              onChange={(e) =>
                setForm({ ...form, email: e.target.value })
              }
              required
            />
          </div>

          {/* Password */}
          <div className="mb-4">
            <label className="block text-white/80 mb-1 text-sm">
              Password
            </label>
            <input
              type="password"
              placeholder="••••••••"
              className="w-full rounded-lg bg-white/10 border border-white/20 px-4 py-3 text-white placeholder-white/40 outline-none focus:border-cyan-400 focus:ring-2 focus:ring-cyan-400/40 transition"
              value={form.password}
              onChange={(e) =>
                setForm({ ...form, password: e.target.value })
              }
              required
            />
          </div>

          {/* Confirm Password */}
          <div className="mb-6">
            <label className="block text-white/80 mb-1 text-sm">
              Confirm Password
            </label>
            <input
              type="password"
              placeholder="••••••••"
              className="w-full rounded-lg bg-white/10 border border-white/20 px-4 py-3 text-white placeholder-white/40 outline-none focus:border-cyan-400 focus:ring-2 focus:ring-cyan-400/40 transition"
              value={form.confirmPassword}
              onChange={(e) =>
                setForm({
                  ...form,
                  confirmPassword: e.target.value,
                })
              }
              required
            />
          </div>

          {/* Role */}
          <div className="mb-6">
            <label className="block text-white/80 mb-1 text-sm">
              Select Role
            </label>
            <select
              className="w-full rounded-lg bg-white/10 border border-white/20 px-4 py-3 text-white outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-400/40 transition"
              value={form.role}
              onChange={(e) =>
                setForm({ ...form, role: e.target.value })
              }
            >
              <option value="STUDENT" className="text-black">
                Student
              </option>
              <option value="ORGANIZER" className="text-black">
                Organizer
              </option>
              <option value="ADMIN" className="text-black">
                Admin
              </option>
            </select>
          </div>

          {/* Button */}
          <button
            type="submit"
            disabled={loading}
            className="w-full rounded-xl bg-gradient-to-r from-green-500 to-cyan-500 py-3 font-semibold text-white hover:from-green-600 hover:to-cyan-600 transition disabled:opacity-60"
          >
            {loading ? "Creating Account..." : "Register"}
          </button>

          {/* Footer */}
          <p className="text-center text-white/70 text-sm mt-6">
            Already have an account?{" "}
            <Link
              href="/login"
              className="text-blue-400 hover:text-blue-300 font-medium transition"
            >
              Login
            </Link>
          </p>
        </form>
      </motion.div>
    </div>
  );
}
