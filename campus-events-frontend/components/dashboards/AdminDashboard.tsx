"use client";

import { useEffect, useState } from "react";
import { getEvents, approveEvent, rejectEvent } from "../../services/api";

export default function AdminDashboard() {
  const [events, setEvents] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [processingId, setProcessingId] = useState<number | null>(null);

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

  async function approve(id: number) {
    try {
      setProcessingId(id);
      await approveEvent(id);
      await fetchEvents();
    } catch (err) {
      console.error("Approve failed", err);
    } finally {
      setProcessingId(null);
    }
  }

  async function reject(id: number) {
    try {
      setProcessingId(id);
      await rejectEvent(id);
      await fetchEvents();
    } catch (err) {
      console.error("Reject failed", err);
    } finally {
      setProcessingId(null);
    }
  }

  if (loading) {
    return <div className="p-8 text-white">Loading events...</div>;
  }

  return (
    <div className="p-8 text-white">
      <h2 className="text-3xl font-bold mb-6">Admin Panel</h2>

      {events.length === 0 && (
        <p className="text-gray-400">No events found.</p>
      )}

      <div className="grid gap-6">
        {events.map((event) => (
          <div key={event.id} className="bg-slate-800 p-6 rounded-xl">
            <h3 className="text-xl font-semibold">{event.title}</h3>
            <p>Status: {event.status}</p>

            {event.status === "PENDING" && (
              <div className="flex gap-4 mt-4">
                <button
                  disabled={processingId === event.id}
                  onClick={() => approve(event.id)}
                  className="bg-green-600 px-4 py-2 rounded disabled:opacity-50"
                >
                  {processingId === event.id ? "Approving..." : "Approve"}
                </button>

                <button
                  disabled={processingId === event.id}
                  onClick={() => reject(event.id)}
                  className="bg-red-600 px-4 py-2 rounded disabled:opacity-50"
                >
                  {processingId === event.id ? "Rejecting..." : "Reject"}
                </button>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
