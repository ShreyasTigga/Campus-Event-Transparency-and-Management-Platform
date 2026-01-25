const API_BASE = "http://localhost:8081/api";

export async function login(email: string, password: string) {
  const res = await fetch(`${API_BASE}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password })
  });

  if (!res.ok) throw new Error("Login failed");
  return res.json();
}

export async function register(data: any) {
  const res = await fetch(`${API_BASE}/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });

  if (!res.ok) throw new Error("Registration failed");
  return res.json();
}

export async function getEvents(token: string, page = 0, size = 6) {
  const res = await fetch(`${API_BASE}/api/events?page=${page}&size=${size}`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });

  if (!res.ok) throw new Error("Failed to load events");
  return res.json();
}

export async function approveEvent(id: number, token: string) {
  return fetch(`${API_BASE}/events/${id}/approve`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
}

export async function rejectEvent(id: number, token: string) {
  return fetch(`${API_BASE}/events/${id}/reject`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
}
