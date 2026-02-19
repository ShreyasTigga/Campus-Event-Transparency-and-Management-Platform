"use client";

import { useEffect, useState } from "react";
import { getEvents, enrollInEvent } from "../../services/api";

export default function StudentDashboard() {
  const [events, setEvents] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [enrollingId, setEnrollingId] = useState<number | null>(null);

  useEffect(() => {
    fetchEvents();
  }, []);

  async function fetchEvents() {
    try {
      setLoading(true);
      const res = await getEvents();
      setEvents(res?.content || []);
    } catch (err) {
      console.error("Failed to fetch events", err);
    } finally {
      setLoading(false);
    }
  }

  async function enroll(eventId: number) {
    try {
      setEnrollingId(eventId);
      await enrollInEvent(eventId);
      alert("Enrolled successfully");
    } catch (err: any) {
      alert(err.message || "Enrollment failed");
    } finally {
      setEnrollingId(null);
    }
  }

  if (loading) {
    return <div className="p-8 text-white">Loading events...</div>;
  }

  return (
    <div className="p-8 text-white">
      <h2 className="text-3xl font-bold mb-6">Available Events</h2>

      {events.length === 0 && (
        <p className="text-gray-400">No events available.</p>
      )}

      <div className="grid gap-6">
        {events.map((event) => (
          <div
            key={event.id}
            className="bg-slate-800 p-6 rounded-xl shadow"
          >
            <h3 className="text-xl font-semibold">{event.title}</h3>
            <p className="text-gray-300">{event.description}</p>

            <button
              disabled={enrollingId === event.id}
              onClick={() => enroll(event.id)}
              className="mt-4 bg-blue-600 px-4 py-2 rounded hover:bg-blue-700 disabled:opacity-50"
            >
              {enrollingId === event.id ? "Enrolling..." : "Enroll"}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
