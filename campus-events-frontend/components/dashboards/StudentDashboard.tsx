"use client";

import { useEffect, useState } from "react";
import api from "../../services/api";

export default function StudentDashboard() {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    fetchEvents();
  }, []);

  async function fetchEvents() {
    try {
      const res = await api.get("/events");
      setEvents(res.data.content);
    } catch (err) {
      console.error(err);
    }
  }

  async function enroll(eventId: number) {
    try {
      await api.post(`/events/${eventId}/enroll`);
      alert("Enrolled successfully");
    } catch (err: any) {
      alert(err.response?.data?.message || "Enrollment failed");
    }
  }

  return (
    <div className="p-8 text-white">
      <h2 className="text-3xl font-bold mb-6">Available Events</h2>

      <div className="grid gap-6">
        {events.map((event: any) => (
          <div
            key={event.id}
            className="bg-slate-800 p-6 rounded-xl shadow"
          >
            <h3 className="text-xl font-semibold">{event.title}</h3>
            <p className="text-gray-300">{event.description}</p>

            <button
              onClick={() => enroll(event.id)}
              className="mt-4 bg-blue-600 px-4 py-2 rounded hover:bg-blue-700"
            >
              Enroll
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
