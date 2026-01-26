"use client";

import { useEffect, useState } from "react";
import { getEvents } from "../../services/api";
import { useAuth } from "../../context/AuthContext";
import Navbar from "../../components/Navbar";
import EventCard from "../../components/EventCard";
import ProtectedRoute from "../../components/ProtectedRoute";

export default function Dashboard() {
  const { token } = useAuth();
  const [events, setEvents] = useState<any[]>([]);
  const [page, setPage] = useState(0);

  async function loadEvents() {
    if (!token) return;

    try {
      const res = await getEvents(token, page, 6);
      setEvents(res.content || res);
    } catch (err) {
      console.error("Failed to load events", err);
    }
  }

  useEffect(() => {
    loadEvents();
  }, [page, token]);

  return (
    <ProtectedRoute>
      <div>
        <Navbar />

        <div className="p-6 grid grid-cols-1 md:grid-cols-3 gap-4">
          {events.map((event) => (
            <EventCard
              key={event.id}
              event={event}
              reload={loadEvents}
            />
          ))}
        </div>

        <div className="flex justify-center gap-4 mb-6">
          <button
            onClick={() => setPage((p) => Math.max(p - 1, 0))}
            className="bg-gray-300 px-4 py-2 rounded"
          >
            Prev
          </button>

          <button
            onClick={() => setPage((p) => p + 1)}
            className="bg-gray-300 px-4 py-2 rounded"
          >
            Next
          </button>
        </div>
      </div>
    </ProtectedRoute>
  );
}
