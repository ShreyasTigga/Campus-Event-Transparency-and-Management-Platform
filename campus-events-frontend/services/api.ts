const API_BASE =
  process.env.NEXT_PUBLIC_API_BASE || "http://localhost:8081/api";

async function request(url: string, options: RequestInit) {
  const res = await fetch(url, options);

  if (!res.ok) {
    const msg = await res.text();
    throw new Error(msg || "API Error");
  }

  return res.json();
}

export function login(email: string, password: string) {
  return request(`${API_BASE}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password }),
  });
}

export function register(data: any) {
  return request(`${API_BASE}/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
}

export function getEvents(token: string, page = 0, size = 6) {
  return request(`${API_BASE}/events?page=${page}&size=${size}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

export function approveEvent(id: number, token: string) {
  return request(`${API_BASE}/events/${id}/approve`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

export function rejectEvent(id: number, token: string) {
  return request(`${API_BASE}/events/${id}/reject`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}
