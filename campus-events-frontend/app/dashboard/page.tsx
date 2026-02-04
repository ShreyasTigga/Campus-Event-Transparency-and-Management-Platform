"use client";

import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import StudentDashboard from "../../components/dashboards/StudentDashboard";
import OrganizerDashboard from "../../components/dashboards/OrganizerDashboard";
import AdminDashboard from "../../components/dashboards/AdminDashboard";
import { useRouter } from "next/navigation";

export default function DashboardPage() {
  const { token, logout } = useAuth();
  const router = useRouter();
  const [role, setRole] = useState<string | null>(null);

  useEffect(() => {
    if (!token) {
      router.push("/login");
      return;
    }

    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      setRole(payload.role);
    } catch {
      logout();
      router.push("/login");
    }
  }, [token, logout, router]);

  if (!role) {
    return (
      <div className="h-screen flex items-center justify-center text-white">
        Loading dashboard...
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-[#020617] via-[#020617] to-[#020617] text-white">
      {role === "STUDENT" && <StudentDashboard />}
      {role === "ORGANIZER" && <OrganizerDashboard />}
      {role === "ADMIN" && <AdminDashboard />}
    </div>
  );
}
