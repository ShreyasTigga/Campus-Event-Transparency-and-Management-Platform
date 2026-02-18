const API_BASE =
  process.env.NEXT_PUBLIC_API_BASE || "http://localhost:8081/api";

/* ---------------------------------- */
/* Helper: Get Token From Storage  */
/* ---------------------------------- */
function getToken() {
  if (typeof window === "undefined") return null;
  return localStorage.getItem("token");
}

/* ---------------------------------- */
/* Core Request Wrapper            */
/* ---------------------------------- */
async function request(
  endpoint: string,
  options: RequestInit = {},
  requiresAuth: boolean = false
) {
const headers: Record<string, string> = {
  "Content-Type": "application/json",
  ...(options.headers as Record<string, string> || {}),
};

if (requiresAuth) {
  const token = getToken();
  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }
}

  const res = await fetch(`${API_BASE}${endpoint}`, {
    ...options,
    headers,
  });

  if (!res.ok) {
    const msg = await res.text();
    throw new Error(msg || "API Error");
  }

  return res.json();
}

/* ---------------------------------- */
/* AUTH APIs                       */
/* ---------------------------------- */
export function login(email: string, password: string) {
  return request("/auth/login", {
    method: "POST",
    body: JSON.stringify({ email, password }),
  });
}

export function register(data: any) {
  return request("/auth/register", {
    method: "POST",
    body: JSON.stringify(data),
  });
}

/* ---------------------------------- */
/* EVENTS APIs                     */
/* ---------------------------------- */
export function getEvents(page = 0, size = 6) {
  return request(`/events?page=${page}&size=${size}`, {}, true);
}

export function approveEvent(id: number) {
  return request(`/events/${id}/approve`, {
    method: "POST",
  }, true);
}

export function rejectEvent(id: number, comment?: string) {
  return request(`/events/${id}/reject`, {
    method: "POST",
    body: JSON.stringify({ comment }),
  }, true);
}

export function enrollInEvent(id: number) {
  return request(`/events/${id}/enroll`, {
    method: "POST",
  }, true);
}

export function deleteEvent(id: number) {
  return request(`/events/${id}`, {
    method: "DELETE",
  }, true);
}

/* ---------------------------------- */
/* DEFAULT EXPORT (IMPORTANT)      */
/* ---------------------------------- */
const api = {
  login,
  register,
  getEvents,
  approveEvent,
  rejectEvent,
  enrollInEvent,
  deleteEvent,
};

export default api;
