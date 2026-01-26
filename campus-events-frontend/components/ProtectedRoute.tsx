"use client";

import { useAuth } from "../context/AuthContext";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function ProtectedRoute({
  children,
  roles,
}: {
  children: React.ReactNode;
  roles?: string[];
}) {
  const { token, role } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!token) {
      router.push("/login");
      return;
    }

    if (roles && role && !roles.includes(role)) {
      router.push("/dashboard");
    }
  }, [token, role, router, roles]);

  if (!token) return null;

  return <>{children}</>;
}
