"use client";

import { useEffect, useState } from "react";
import { getEvents, approveEvent, rejectEvent } from "../../services/api";

export default function AdminDashboard() {
  const [events, setEvents] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [processingId, setProcessingId] = useState<number | null>(null);
  const [activeTab, setActiveTab] = useState("PENDING");

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

  const filteredEvents =
    activeTab === "ALL"
      ? events
      : events.filter((e) => e.status === activeTab);

  const pendingCount = events.filter(e => e.status === "PENDING").length;
  const approvedCount = events.filter(e => e.status === "APPROVED").length;
  const rejectedCount = events.filter(e => e.status === "REJECTED").length;

  if (loading) {
    return <div className="p-8 text-white">Loading events...</div>;
  }

  return (
    <div className="p-8 text-white">

      <h2 className="text-3xl font-bold mb-6">Admin Panel</h2>

      {/* SUMMARY CARDS */}
      <div className="grid md:grid-cols-3 gap-6 mb-8">
        <div className="bg-slate-800 p-6 rounded-xl">
          <p className="text-gray-400">Pending</p>
          <p className="text-3xl">{pendingCount}</p>
        </div>

        <div className="bg-slate-800 p-6 rounded-xl">
          <p className="text-gray-400">Approved</p>
          <p className="text-3xl">{approvedCount}</p>
        </div>

        <div className="bg-slate-800 p-6 rounded-xl">
          <p className="text-gray-400">Rejected</p>
          <p className="text-3xl">{rejectedCount}</p>
        </div>
      </div>

      {/* FILTER TABS */}
      <div className="flex gap-6 mb-6 border-b border-slate-700 pb-3">
        {["PENDING", "APPROVED", "REJECTED", "ALL"].map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            className={`${
              activeTab === tab
                ? "text-blue-400 font-semibold"
                : "text-gray-400"
            }`}
          >
            {tab}
          </button>
        ))}
      </div>

      {/* EVENTS LIST */}
      {filteredEvents.length === 0 ? (
        <p className="text-gray-400">No events in this category.</p>
      ) : (
        <div className="grid gap-6">
          {filteredEvents.map((event) => (
            <div key={event.id} className="bg-slate-800 p-6 rounded-xl">

              <div className="flex justify-between items-center">
                <h3 className="text-xl font-semibold">
                  {event.title}
                </h3>

                {/* STATUS BADGE */}
                <span
                  className={`px-3 py-1 rounded text-sm ${
                    event.status === "APPROVED"
                      ? "bg-green-600"
                      : event.status === "REJECTED"
                      ? "bg-red-600"
                      : "bg-yellow-600"
                  }`}
                >
                  {event.status}
                </span>
              </div>

              <p className="text-gray-300 mt-2">
                {event.description}
              </p>

              {/* ACTION BUTTONS */}
              {event.status === "PENDING" && (
                <div className="flex gap-4 mt-4">
                  <button
                    disabled={processingId === event.id}
                    onClick={() => approve(event.id)}
                    className="bg-green-600 px-4 py-2 rounded disabled:opacity-50"
                  >
                    {processingId === event.id
                      ? "Approving..."
                      : "Approve"}
                  </button>

                  <button
                    disabled={processingId === event.id}
                    onClick={() => reject(event.id)}
                    className="bg-red-600 px-4 py-2 rounded disabled:opacity-50"
                  >
                    {processingId === event.id
                      ? "Rejecting..."
                      : "Reject"}
                  </button>
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}