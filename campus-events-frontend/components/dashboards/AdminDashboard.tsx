"use client";

import { useEffect, useState } from "react";
import { getEvents, approveEvent, rejectEvent } from "../../services/api";

export default function AdminDashboard() {
  const [events, setEvents] = useState<any[]>([]);

  useEffect(() => {
    fetchEvents();
  }, []);

  async function fetchEvents() {
    const res = await getEvents();
    setEvents(res.content);
  }

  async function approve(id: number) {
    await approveEvent(id);
    fetchEvents();
  }

  async function reject(id: number) {
    await rejectEvent(id);
    fetchEvents();
  }

  return (
    <div className="p-8 text-white">
      <h2 className="text-3xl font-bold mb-6">Admin Panel</h2>

      <div className="grid gap-6">
        {events.map((event) => (
          <div key={event.id} className="bg-slate-800 p-6 rounded-xl">
            <h3 className="text-xl font-semibold">{event.title}</h3>
            <p>Status: {event.status}</p>

            {event.status === "PENDING" && (
              <div className="flex gap-4 mt-4">
                <button
                  onClick={() => approve(event.id)}
                  className="bg-green-600 px-4 py-2 rounded"
                >
                  Approve
                </button>

                <button
                  onClick={() => reject(event.id)}
                  className="bg-red-600 px-4 py-2 rounded"
                >
                  Reject
                </button>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
