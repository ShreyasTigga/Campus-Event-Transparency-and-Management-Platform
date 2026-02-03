"use client";

import { motion } from "framer-motion";
import Link from "next/link";

export default function HomePage() {
  return (
    // Changed to a deep dark gradient (slate-950 to black)
    <div className="relative min-h-screen overflow-hidden bg-gradient-to-br from-slate-950 via-gray-900 to-black">

      {/* Dark overlay for extra depth */}
      <div className="absolute inset-0 bg-black/40" />

      {/* Adjusted glow orbs to be subtler for a dark theme */}
      <div className="absolute -top-40 -left-40 w-96 h-96 bg-blue-600/20 rounded-full blur-[100px]" />
      <div className="absolute top-1/3 -right-40 w-96 h-96 bg-purple-600/20 rounded-full blur-[100px]" />
      <div className="absolute bottom-0 left-1/3 w-96 h-96 bg-cyan-600/20 rounded-full blur-[100px]" />

      {/* Content: Removed bg-white/60, added bg-white/5 for a subtle glass effect */}
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 1 }}
        className="relative z-10 flex flex-col items-center justify-center min-h-screen px-6 text-center bg-white/5 backdrop-blur-lg rounded-3xl border border-white/10 shadow-2xl max-w-4xl mx-auto"
      >
        <h1 className="text-6xl md:text-8xl font-extrabold text-slate-50 drop-shadow-2xl mb-6">
          Campus Event Transparency
        </h1>

        {/* Text changed to a high-contrast light shade (slate-200) */}
        <p className="text-slate-200 max-w-2xl mb-10 text-lg md:text-xl drop-shadow-md">
          A modern platform for students, organizers, and administrators to
          manage campus events with full transparency, real-time approvals, and
          role-based access control.
        </p>

        <div className="flex gap-6">
          <Link
            href="/login"
            className="px-8 py-4 rounded-xl bg-white text-black font-bold text-lg hover:bg-slate-200 transition shadow-xl"
          >
            Login
          </Link>

          <Link
            href="/register"
            className="px-8 py-4 rounded-xl bg-blue-600 text-white font-bold text-lg hover:bg-blue-500 transition shadow-xl"
          >
            Register
          </Link>
        </div>
      </motion.div>
    </div>
  );
}