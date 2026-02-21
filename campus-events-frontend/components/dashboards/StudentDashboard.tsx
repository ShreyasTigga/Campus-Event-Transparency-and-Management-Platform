"use client";

import { useEffect, useState } from "react";
import { getEvents, enrollInEvent } from "../../services/api";

export default function StudentDashboard() {
  const [activeTab, setActiveTab] = useState("overview");
  const [events, setEvents] = useState<any[]>([]);
  const [enrolledEvents, setEnrolledEvents] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [enrollingId, setEnrollingId] = useState<number | null>(null);

  useEffect(() => {
    fetchEvents();
  }, []);

  async function fetchEvents() {
    try {
      setLoading(true);
      const res = await getEvents();
      const eventList = res?.content || [];
      setEvents(eventList);

      // Simulate enrolled (replace with real API later)
      setEnrolledEvents(eventList.filter((e: any) => e.status === "APPROVED"));
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  }

  async function enroll(eventId: number) {
    try {
      setEnrollingId(eventId);
      await enrollInEvent(eventId);
      alert("Enrolled successfully");
      fetchEvents();
    } catch (err: any) {
      alert(err.message || "Enrollment failed");
    } finally {
      setEnrollingId(null);
    }
  }

  /* =================== UI =================== */

  return (
    <div className="p-8 text-white">

      {/* HEADER */}
      <h2 className="text-3xl font-bold mb-6">
        Student Dashboard
      </h2>

      {/* TABS */}
      <div className="flex gap-6 mb-8 border-b border-slate-700 pb-3">
        {["overview", "browse", "enrolled", "saved"].map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            className={`capitalize ${
              activeTab === tab
                ? "text-blue-400 font-semibold"
                : "text-gray-400"
            }`}
          >
            {tab}
          </button>
        ))}
      </div>

      {/* =================== OVERVIEW =================== */}
      {activeTab === "overview" && (
        <div className="grid md:grid-cols-3 gap-6">
          <div className="bg-slate-800 p-6 rounded-xl">
            <h3 className="text-lg font-semibold">Total Events</h3>
            <p className="text-3xl mt-2">{events.length}</p>
          </div>

          <div className="bg-slate-800 p-6 rounded-xl">
            <h3 className="text-lg font-semibold">Enrolled</h3>
            <p className="text-3xl mt-2">
              {enrolledEvents.length}
            </p>
          </div>

          <div className="bg-slate-800 p-6 rounded-xl">
            <h3 className="text-lg font-semibold">
              Upcoming Events
            </h3>
            <p className="text-3xl mt-2">
              {
                enrolledEvents.filter(
                  (e) => new Date(e.startTime) > new Date()
                ).length
              }
            </p>
          </div>
        </div>
      )}

      {/* =================== BROWSE =================== */}
      {activeTab === "browse" && (
        <div>
          {loading ? (
            <p>Loading events...</p>
          ) : (
            <div className="grid gap-6">
              {events.map((event) => (
                <div
                  key={event.id}
                  className="bg-slate-800 p-6 rounded-xl"
                >
                  <h3 className="text-xl font-semibold">
                    {event.title}
                  </h3>
                  <p className="text-gray-300">
                    {event.description}
                  </p>

                  <button
                    disabled={enrollingId === event.id}
                    onClick={() => enroll(event.id)}
                    className="mt-4 bg-blue-600 px-4 py-2 rounded disabled:opacity-50"
                  >
                    {enrollingId === event.id
                      ? "Enrolling..."
                      : "Enroll"}
                  </button>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {/* =================== ENROLLED =================== */}
      {activeTab === "enrolled" && (
        <div>
          {enrolledEvents.length === 0 ? (
            <p className="text-gray-400">
              No enrolled events yet.
            </p>
          ) : (
            <div className="grid gap-6">
              {enrolledEvents.map((event) => (
                <div
                  key={event.id}
                  className="bg-slate-800 p-6 rounded-xl"
                >
                  <h3 className="text-xl font-semibold">
                    {event.title}
                  </h3>
                  <p>Status: {event.status}</p>
                  <p>
                    Starts:{" "}
                    {new Date(event.startTime).toLocaleString()}
                  </p>
                </div>
              ))}
            </div>
          )}
        </div>
      )}

      {/* =================== SAVED =================== */}
      {activeTab === "saved" && (
        <div>
          <p className="text-gray-400">
            Bookmark feature coming soon...
          </p>
        </div>
      )}
    </div>
  );
}